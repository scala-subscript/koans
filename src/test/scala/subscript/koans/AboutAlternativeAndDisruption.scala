package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutAlternativeAndDisruption extends KoanSuite with OperatorKoansHelper {

  koan("""Alternative operator `+` executes that of its operands which
        | started first, excluding remaining ones as soon as the operand
        | starts its execution.""") {
    script s = [b c + d e + f g] h

    runWithInput(s)()
    activatedShouldBe(b, d, f)

    runWithInput(s)(b)
    activatedShouldBe(c)

    runWithInput(s)(d)
    activatedShouldBe(e)

    runWithInput(s)(f, g)
    activatedShouldBe(h)
  }

  koan("""Disruption operator `/` terminates its left-hand side once its
        | right-hand side starts execution. If left-hand side finishes its
        | execution without disruption, right-hand side is excluded.""") {
    script s = b c / d e

    runWithInput(s)()
    activatedShouldBe(b, d)

    runWithInput(s)(b)
    activatedShouldBe(c, d)

    runWithInput(s)(b, c)
    activatedShouldBe()


    runWithInput(s)(d)
    activatedShouldBe(e)

    runWithInput(s)(b, d)
    activatedShouldBe(e)

    runWithInput(s)(d, e)
    activatedShouldBe()
  }

}