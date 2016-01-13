package subscript.koans.util

import subscript.language
import subscript.Predef._
import subscript.objectalgebra._

import subscript.vm._

import org.scalatest._

trait OperatorKoansHelper {this: Matchers =>

  class RecordingTrigger(name: String) extends Trigger {
    var activatedFlag = false

    override script lifecycle = @{
      there.onActivate {
        println("Activated " + this)
        activatedFlag = true
        activated += this
      }

      there.onSuccess  {
        println("Succeeded " + this)
        activated  -= this
        succeeded :+= this
      }

      there.onExclude {
        println("Excluded " + this)
        if (!runIsOver) activated -= this
      }
    }: super.lifecycle

    override def toString = name
  }

  var succeeded: List[RecordingTrigger] = Nil
  var activated: Set [RecordingTrigger] = Set()
  var runIsOver = true
  def reset() {
    succeeded = Nil
    activated = Set()
    runIsOver = true
  }


  def checkCollection(c: Iterable[RecordingTrigger], ts: Seq[Any]) {
    if (ts.isEmpty) c shouldBe 'empty
    else c should contain only (ts: _*)
  }
  def succeededShouldBe(ts: Any*) = checkCollection(succeeded, ts)
  def activatedShouldBe(ts: Any*) = checkCollection(activated, ts)


  val b = new RecordingTrigger("b")
  val c = new RecordingTrigger("c")
  val d = new RecordingTrigger("d")
  val e = new RecordingTrigger("e")
  val f = new RecordingTrigger("f")

  def runWithInput(s: ScriptNode[Any])(input: RecordingTrigger*) {
    reset()
    runScript(runWithInputScript(s, input))
  }

  val sampleStopper = new Trigger


  script..
    runWithInputScript(s: ScriptNode[Any], input: Seq[RecordingTrigger]) =
      input.foreach(t => t.activatedFlag = false)
      let runIsOver = false
      [s [-] / sampleStopper reset()] || @{there.onExclude {println("Triggers excluded")}}: fireTriggers: input

    fireTriggers(input: Seq[RecordingTrigger]) =
      var i = 0
      [
        while(i < input.size)
        triggerWithin: 50, input(i)
        let i += 1
      ]
      sleep: 1000
      let runIsOver = true

    // Waits for the trigger script to be activated, but only for maxDelay
    triggerWithin(maxDelay: Long, t: RecordingTrigger) =
      var start = System.currentTimeMillis
      [while(!t.activatedFlag) sleep: 10]
      if System.currentTimeMillis - start <= maxDelay then t.trigger else sampleStopper.trigger

}