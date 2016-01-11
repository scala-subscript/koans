package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutParallelOperators extends KoanSuite with OperatorKoansHelper {

  koan("""Parallel operator `||` waits for at least one of
        | its operands to succeed, then succeeds""") {
    script s = [b c || d e] f

    // runWithInput(s)()
    // succeeded shouldBe 'empty
    // activated should contain only (b, d)

    runWithInput(s)(b, d, e)
    activated should contain only (f)
  }

}