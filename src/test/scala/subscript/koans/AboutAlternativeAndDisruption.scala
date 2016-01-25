package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutAlternativeAndDisruption extends KoanSuite with OperatorKoansHelper {

  koan("""Alternative operator `+` executes that of its operands which
        | started first, excluding remaining ones as soon as the operand
        | starts its execution.""") {
    script s = [b c + d e + f g] h

    test(1) {
      runWithInput(s)()
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s)(___)
      activatedShouldBe(c)
    }

    test(3) {
      runWithInput(s)(d)
      activatedShouldBe(___)
    }

    test(4) {
      runWithInput(s)(___)
      activatedShouldBe(h)
    }
  }

  koan("""Disruption operator `/` terminates its left-hand side once its
        | right-hand side starts execution. If left-hand side finishes its
        | execution without disruption, right-hand side is excluded.""") {
    script s = b c / d e

    test(1) {
      runWithInput(s)()
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s)(___)
      activatedShouldBe(c, d)
    }

    test(3) {
      runWithInput(s)(b, c)
      activatedShouldBe(___)
    }


    test(4) {
      runWithInput(s)(d)
      activatedShouldBe(e)
    }

    test(5) {
      runWithInput(s)(b, d)
      activatedShouldBe(___)
    }

    test(6) {
      runWithInput(s)(d, e)
      activatedShouldBe(___)
    }
  }

}