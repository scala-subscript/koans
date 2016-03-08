package subscript.koans.util

import subscript.language
import subscript.Predef._
import subscript.objectalgebra._

import subscript.vm._
import subscript.vm.AAHappened
import subscript.vm.model.callgraph.CallGraphNode

import scala.collection.mutable.ListBuffer

import org.scalatest._

trait OperatorKoansHelper {this: Matchers =>

  class RecordingTrigger(name: String) extends Trigger {
    var activatedFlag = false

    override script lifecycle = @{
      // def dbg(x: Any) = if (!runIsOver) println(x)

      there.onActivate {
        // dbg("Activated " + this)
        activatedFlag = true
        _activated += this
      }

      there.onSuccess  {
        // dbg("Succeeded " + this)
        _activated  -= this
        succeeded :+= this
      }

      there.onExclude {
        // dbg("Excluded " + this)
        if (!runIsOver) _activated -= this
        activatedFlag = false
      }

      there.onDeactivate {
        // dbg("Deactivated " + this + "\n")
        activatedFlag = false
      }
    }: super.lifecycle

    override def toString = name
  }

  var succeeded : List      [RecordingTrigger] = Nil
  var _activated: ListBuffer[RecordingTrigger] = new ListBuffer
  def activated : Set       [RecordingTrigger] = _activated.toSet

  var runIsOver = true
  def reset() {
    succeeded  = Nil
    _activated = new ListBuffer
    runIsOver  = true
  }


  def checkCollection(c: Iterable[RecordingTrigger], ts: Seq[Any]) {
    if (ts.isEmpty) c shouldBe 'empty
    else c should contain only (ts: _*)
  }
  def succeededShouldBe(ts: Any*) = checkCollection(succeeded, ts)
  def thenActivatedOrSuccess(ts: Any*) = checkCollection(activated, ts)


  val b = new RecordingTrigger("b")
  val c = new RecordingTrigger("c")
  val d = new RecordingTrigger("d")
  val e = new RecordingTrigger("e")
  val f = new RecordingTrigger("f")
  val g = new RecordingTrigger("g")
  val h = new RecordingTrigger("h")
  val S = new RecordingTrigger("S")

  val `___` = new RecordingTrigger("___")

  def runWithInput(s: Script[Any])(input: RecordingTrigger*) {
    reset()
    runScript(runWithInputScript(s, input))
  }

  val sampleStopper = new Trigger


  script..
    runWithInputScript(s: Script[Any], input: Seq[RecordingTrigger]) =
      input.foreach(t => t.activatedFlag = false)
      let S.activatedFlag = false
      let runIsOver = false
      [s S [-] / sampleStopper reset()] || fireTriggers: input

    fireTriggers(input: Seq[RecordingTrigger]) =
      var i = 0
      [
        while(i < input.size)
        triggerWithin: 500, input(i)
        let i += 1
      ]
      noMessagesBeforeMe
      let runIsOver = true

    // Waits for the trigger script to be activated, but only for maxDelay
    triggerWithin(maxDelay: Long, t: RecordingTrigger) =
      var start = System.currentTimeMillis
      noMessagesBeforeMe
      t.trigger

    noMessagesBeforeMe =
      var target: CallGraphNode = null
      @absorbAAHappened(target): [
        var flag = false
        {:flag = here.scriptExecutor.msgQueue.collection.filter(_.node.index < here.index).size > 1:}      
        while(flag)
        @{target = here}: {*Thread sleep 10*}
      ]

  def absorbAAHappened(target: => CallGraphNode)(implicit there: CallGraphNode) {
    val se = there.scriptExecutor
    se.msgHandlers.sInsert {
      case AAHappened(n, c, _) if (n eq there) && (c eq target) => se.msgQueue.dequeue(Int.MinValue)
    }
  }
}
