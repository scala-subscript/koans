package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutAndParallelism extends KoanSuite with OperatorKoansHelper {

  koan("""And-parallel operator `&` executes all its operands in parallel.
        | It succeeds only if each of its children
        | succeed - hence and-parallelism.
        |
        | `[` and `]` are parentheses specifying operators precedence.
        |
        | Space operator has the smallest precedence.
        | In the following koan, `b c` sequence executes in parallel with
        | `d e` sequence, this parallelism in sequence with `f`.""") {
    script s = [b c & d e] f

    runWithInput(s)()
    activatedShouldBe(__)

    runWithInput(s)(b)
    activatedShouldBe(__)

    runWithInput(s)(b, c)
    activatedShouldBe(__)

    runWithInput(s)(b, c, d, e)
    activatedShouldBe(__)
  }

  koan("""And-parallel operator `&` doesn't succeed if at least
        | one of its children doesn't.""") {
    script s = [b c & d [-]] f

    runWithInput(s)(d)
    activatedShouldBe(__)

    runWithInput(s)(b, c, d)
    activatedShouldBe(__)
  }

  koan("""Non-strict and-parallelism operator `&&` behaves like `&`,
        | but if one of its children doesn't succeed, it will terminate
        | without success immediately, terminating all its remaining
        | operands.""") {
    script s = [b c && d [-]] f

    runWithInput(s)()
    activatedShouldBe(__)

    runWithInput(s)(d)
    activatedShouldBe(__)
  }

}