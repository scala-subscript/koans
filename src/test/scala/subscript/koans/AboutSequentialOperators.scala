package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutSequentialOperators extends KoanSuite with OperatorKoansHelper {
  koan("""Sequential operator, space, executes its operands sequentialy""") {
    script s = b c d

    runWithInput(s)(b, c)

    succeeded should contain only (b, c)
    activated should contain only (__)
  }
}