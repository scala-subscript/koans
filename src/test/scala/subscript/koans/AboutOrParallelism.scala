package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutOrParallelism extends KoanSuite with OperatorKoansHelper {

  koan("""Non-strict or-parallel operator `||` executes its children in
        | parallel. It succeeds if at least one of its operands does.
        | After some of its operands has success, all the other ones
        | are excluded (terminated).""") {
    script..
      s1 = [b c || d   e] f
      s2 = [b c || d [-]] f

    runWithInput(s1)()
    activatedShouldBe(___)

    runWithInput(s1)(b, c)
    activatedShouldBe(___)

    runWithInput(s2)(d, ___)
    activatedShouldBe(f)
  }

  koan("""Strict or-parallel operator `|` behaves like `||`, but it
        | does not exclude its operands upon first success.
        |
        | Some operators can have success multiple times; when we say
        | that an operator succeeded, we don't necessarily mean that it
        | terminated. `|` is one of such operators: it succeeds once
        | one of its operands does, but continues to execute remaining ones.
        |
        | Note also that a sequential operators' behaviour on success of
        | its operand is to execute the next operand (if there is such) and
        | exclude all the currently executing operands (if there are such).
        |
        | `|` is not used often, so don't worry if it seems too complicated.""") {
    script..
      s1 =  b c | d e
      s2 = [b c | d e] f

    runWithInput(s1)()
    activatedShouldBe(b, d)

    runWithInput(s1)(___)
    activatedShouldBe(d)

    runWithInput(s1)(b, c, d)
    activatedShouldBe(___)

    runWithInput(s1)(___)
    activatedShouldBe()


    runWithInput(s2)()
    activatedShouldBe(___)

    runWithInput(s2)(___)
    activatedShouldBe(d, f)

    runWithInput(s2)(b, c, f)
    activatedShouldBe(___)
  }
}