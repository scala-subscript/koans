package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutParallelOperators extends KoanSuite with OperatorKoansHelper {

  koan("""Parallel operator `||` waits for at least one of
        | its operands to succeed, then succeeds""") {
    script s = [b c || d e] f

    runWithInput(s)()
    succeededShouldBe()
    activatedShouldBe(b, d)

    runWithInput(s)(b, d, e)
    activatedShouldBe(f)
  }

  koan("""Parallel operator `&&` waits for all of the operands to
        | succeed then succeeds. It fails if at least one of them fail.""") {
    script s = [b c && d e] f

    runWithInput(s)(b, d, e)
    activatedShouldBe(c)

    runWithInput(s)(b, d, c, e)
    activatedShouldBe(f)

    script s2 = [b c && d [-]] f
    runWithInput(s2)(b, c, d)
    activatedShouldBe()
  }

}