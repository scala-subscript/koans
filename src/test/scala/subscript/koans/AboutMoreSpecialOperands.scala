package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutMoreSpecialOperands extends KoanSuite with OperatorKoansHelper {

  koan("""Loop, `...`, is an operand that loops its parent sequence.""") {
    script s = b c ...
    
    runWithInput(s)(b, c, b, c, b)
    activatedShouldBe(c)
  }

  koan("""It doesn't matter where you put `...` in a sequence for it to have
        | effect.""") {
    script..
      s1 = b ... c
      s2 = ... b c

    runWithInput(s1)(b, c)
    activatedShouldBe(b)

    runWithInput(s2)(b, c, b)
    activatedShouldBe(c)
  }

}