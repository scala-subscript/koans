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

    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""The remark in the previous koan about the index of the
        | result value in the sequnce is important.""") {
    script s = var i = 0
               [while(i < 4) {!i!}^^ {!i += 1!}]

               let i = 0
               [while(i < 3) {!i += 1!} {!i!}^^]

    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""Double caret followed by a number (`^^1`, `^^2` etc) makes
        | the result of a script to be a tuple and places the result
        | of the current operand to the specified position in the
        | tuple.""") {
    script s = {!1!}^^2 {!2!}^^1

    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""You can use all te above mentioned syntax with literals and
        | vars if you first prefix them with `^`.""") {
    script..
      s1 = var i = 0
           [while(i < 4) ^i^^ let i += 1]

      s2 = ^1^^1 ^2^^2

    test(1) {
      runScript(s1).$ shouldBe __
    }

    test(2) {
      runScript(s2).$ shouldBe __
    }
  }
  
}