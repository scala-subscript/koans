package subscript.koans.util

import org.scalatest._
import org.scalatest.events._
import org.scalatest.exceptions.TestFailedException

import subscript.swing.SubScriptDebuggerApp
import subscript.vm.executor.ScriptExecutorFactory

object KoanSuiteGlobal {
  def env(name: String): Option[String] = {
    val res = System.getProperty(name)
    if (res ne null) Some(res) else None
  }

  def envSeq(name: String): Seq[String] =
    env(name).map(_.split(',').toList).getOrElse(Nil)

  def envSeqPredicate(name: String)(predicate: String => Boolean): Boolean = {
    val seq = envSeq(name)
    seq.isEmpty || seq.exists(predicate)
  }

  val wrongTestsMax = env("max").map(_.toInt).getOrElse(1)
  var wrongTests = 0

  val showAll = env("show").map(_.toInt != 0).getOrElse(false)

  def doStop = wrongTests >= wrongTestsMax

  var testStack: List[String] = Nil
}

trait KoanSuite extends FunSuite with KoanPredef
                                 with Matchers
                                 with KoanSuiteEngine {
  import KoanSuiteGlobal._

  override def runTests(name: Option[String], args: Args) = name match {
    case Some(_) => super.runTests(name, args)
    case None    => testNames.foldLeft(SucceededStatus: Status) {(status, test) =>
      if (!doStop) {
        val result = runTest(test, args)
        if (doStop) args.stopper.requestStop()
        result
      }
      else status
    }
  }

  def koan(id: Int)(name: String)(fun: => Unit) = test(s"Koan $id") {
    if (envSeqPredicate("koan")(_.toInt == id)) {
      resetEngine()
      fun
      processTests()
    }
  }

  def test(id: Int)(fun: => Unit): Unit = tests :+=
    KoanTest(id, () => fun, false)

  def show(id: Int)(fun: => Unit): Unit = tests :+=
    KoanTest(id, () => fun, true)

}

trait KoanSuiteEngine {
  import KoanSuiteGlobal._

  case class KoanTest(id: Int, payload: () => Unit, show: Boolean)

  var tests: List[KoanTest] = Nil
  var msgs : List[String  ] = Nil
  def resetEngine() {
    tests = Nil
    msgs  = Nil
  }

  def processTests() {
    // println(s"KoanId: $koanId; class: $className")

    @annotation.tailrec
    def loop(tx: List[KoanTest]): Unit = tx match {
      case t :: x if !doStop =>
        // Debug or not debug?
        if ( env("debug").map(_.toInt != 0      ).getOrElse(false)  // Debug allowed
          && env("test" ).map(_.toInt == t.id   ).getOrElse(false)  // THis is the test to debug
        ) {
          val debugger = new SubScriptDebuggerApp {
            vmThread = new Thread{override def run={
              live
              top.visible = false
            }}
          }
          ScriptExecutorFactory.addScriptDebugger(debugger)
          debugger.top.visible = true
          debugger.vmThread.start()
        }

        // Execute the test
        try t.payload()
        catch {case e: TestFailedException =>
          wrongTests += 1
          msgs :+= {
            if (t.show || showAll) s"Test ${t.id}: ${e.getMessage}"
            else s"Test ${t.id} goes wrong: try again please."
          }  
        }

        loop(x)

      case _ =>
    }

    loop(tests)
    if (!msgs.isEmpty) throw new TestFailedException(msgs.mkString("\n"), 0)
  }

}
