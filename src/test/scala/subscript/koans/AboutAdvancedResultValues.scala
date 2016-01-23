package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutAdvancedResultValues extends KoanSuite {

  koan("""Double caret `^^` after an operand makes a result of a script
        | to be `Seq[Any]` instead of `Any` (wrapped in a `Try` of course).
        | Each invocation of this operand will record its result to that
        | `Seq`, and the result's index will be equal to the number of
        | invocations this operand already had.""") {
    script s = var i = 0
               [while(i < 3) {!i!}^^ {!i += 1!}]

    runScript(s).$ shouldBe Success(Seq(0, 1, 2))
  }
  
}