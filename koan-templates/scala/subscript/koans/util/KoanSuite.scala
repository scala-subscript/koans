package subscript.koans.util

import org.scalatest._
import org.scalatest.events._
import org.scalatest.exceptions.TestFailedException

trait KoanSuite extends FunSuite with KoanPredef
                                 with Matchers {
  
  override def runTests(name: Option[String], args: Args) = name match {
    case Some(_) => super.runTests(name, args)
    case None    => testNames.foldLeft(SucceededStatus: Status) {(status, test) =>
      if  (status == SucceededStatus) {
        val result = runTest(test, args)
        if (result == FailedStatus) args.stopper.requestStop()
        result
      }
      else status
    }
  }

  def koan         (name: String)(fun: => Unit) = test(name.stripMargin)(fun)
  def koan(id: Int)(name: String)(fun: => Unit) = test(s"Koan $id"     )(fun)

  def test(id: Int)(fun: => Unit) {
    try fun
    catch {
      case _: TestFailedException => throw new TestFailedException(s"Test $id goes wrong: try again please.", 0)
    }
  }

  def show(id: Int)(fun: => Unit) = withClue(s"Test $id:")(fun)

}
