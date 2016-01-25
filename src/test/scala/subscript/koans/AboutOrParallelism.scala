package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutOrParallelism extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """Non-strict or-parallel operator `||` executes its children in
    | parallel. It succeeds if at least one of its operands does.
    | After some of its operands has success, all the other ones
    | are excluded (terminated)."""
  ) {
    script..
      s1 = [b c || d   e] f
      s2 = [b c || d [-]] f
    
    test(1) {
      runWithInput(s1)()
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s1)(b, c)
      activatedShouldBe(___)
    }

    test(3) {
      runWithInput(s2)(d, ___)
      activatedShouldBe(f)
    }
  }

  koan(2)(
    """Strict or-parallel operator `|` behaves like `||`, but it
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
    | `|` is not used often, so don't worry if it seems too complicated."""
  ) {
    script..
      s1 =  b c | d e
      s2 = [b c | d e] f

    test(1) {
      runWithInput(s1)()
      activatedShouldBe(b, d)
    }

    test(2) {
      runWithInput(s1)(___)
      activatedShouldBe(d)
    }

    test(3) {
      runWithInput(s1)(b, c, d)
      activatedShouldBe(___)
    }

    test(4) {
      runWithInput(s1)(___)
      activatedShouldBe()
    }


    test(5) {
      runWithInput(s2)()
      activatedShouldBe(___)
    }

    test(6) {
      runWithInput(s2)(___)
      activatedShouldBe(d, f)
    }

    test(7) {
      runWithInput(s2)(b, c, f)
      activatedShouldBe(___)
    }
  }
}