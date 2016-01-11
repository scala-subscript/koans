package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutSequentialOperators extends KoanSuite with OperatorKoansHelper {

  koan("""Sequential operator, space, executes its operands sequentialy""") {
    script s = b c d

    runWithInput(s)(b, c)

    succeededShouldBe(b, c)
    activatedShouldBe(d)
  }

  koan("""`;` is also a sequential operator""") {
    script s = b; c; d

    runWithInput(s)(b)

    succeededShouldBe(b)
    activatedShouldBe(c)
  }

  koan("""Line break is also a sequential operator.
        | Note that the indentation of all the script lines
        | must be greater or equal to that of the first line.""") {
    script s = b
               c
               d

    runWithInput(s)()

    succeededShouldBe()
    activatedShouldBe(b)
  }

}