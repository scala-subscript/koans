package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutEpsilonAndDelta extends KoanSuite with OperatorKoansHelper {

  koan("""Epsilon, `[+]`, is a special operand that succeeds immediately
        | after activation. It signifies an ACP empty action.""") {
    script s = b [+] c

    runWithInput(s)(b)
    activatedShouldBe(__)
  }

  koan("""Delta, `[-]`, is a special operand that never succeeds.
        | It signifies an ACP deadlock.""") {
    script s = b [-] c

    runWithInput(s)(b)
    activatedShouldBe(__)
  }

}