package subscript.koans.util

import subscript.language
import subscript.Predef._
import subscript.objectalgebra._

import subscript.vm._

trait OperatorKoansHelper {

  class RecordingTrigger(name: String) extends Trigger {
    var activatedFlag = false

    override script lifecycle = @{
      there.onActivate {
        activatedFlag = true
        activated += this
      }

      there.onSuccess  {
        activated  -= this
        succeeded :+= this
      }
    }: super.lifecycle

    override def toString = name
  }

  var succeeded: List[RecordingTrigger] = Nil
  var activated: Set [RecordingTrigger] = Set()
  def reset() {
    succeeded = Nil
    activated = Set()
  }
  
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
      [s / sampleStopper reset()] || fireTriggers: input

    fireTriggers(input: Seq[RecordingTrigger]) =
      var i = 0
      [
        while(i < input.size)
        triggerWithin: 50, input(i)
        let i += 1
      ]

    // Waits for the trigger script to be activated, but only for maxDelay
    triggerWithin(maxDelay: Long, t: RecordingTrigger) =
      var start = System.currentTimeMillis
      [while(!t.activatedFlag) sleep: 10]
      if System.currentTimeMillis - start <= maxDelay then t.trigger else sampleStopper.trigger

}