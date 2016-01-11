package subscript.koans.util

import org.scalatest._
import org.scalatest.events._

trait KoanSuite extends FunSuite with KoanPredef
                                 with Matchers {
  
  override def runTests(name: Option[String], args: Args) = name match {
    case Some(_) => super.runTests(name, args)
    case None    => testNames.foldLeft(SucceededStatus: Status) {(status, test) =>
      if  (status == SucceededStatus) runTest(test, args)
      else status 
    }
  }

  def koan(name: String)(fun: => Unit) = test(name.stripMargin)(fun)
  
}
