package subscript.koans.util

import org.scalatest._
import org.scalatest.events._
import org.scalatest.exceptions.TestFailedException

object KoanSuiteGlobal {
  def env(name: String): Option[String] = {
    val res = System.getProperty(name)
    if (res ne null) Some(res) else None
  }

  val wrongTestsMax = env("max").map(_.toInt).getOrElse(1)
  var wrongTests = 0

  val showAll = env("show").map(_ != 0).getOrElse(false)

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
        // if (result == FailedStatus) wrongTests += 1
        if (doStop) args.stopper.requestStop()
        result
      }
      else status
    }
  }

  def koan(id: Int)(name: String)(fun: => Unit) = test(s"Koan $id"     ) {
    resetEngine()
    fun
    processTests()
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
    @annotation.tailrec
    def loop(tx: List[KoanTest]): Unit = tx match {
      case t :: x if !doStop =>
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