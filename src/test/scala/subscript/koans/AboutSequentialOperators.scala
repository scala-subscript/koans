package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutSequentialOperators extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """In SubScript, operands are activated before execution.
    | When we say "operand is activated", we mean that it can be
    | executed.
    |
    | Operators are also valid operands, so same logic is applicable to them.
    |
    | When an operator is executed, it can finish its execution either
    | successfuly (with success) or not (e.g. an exception happened).
    |
    | Sequential operator, space, executes its operators sequentialy.
    | It means that at the very beginning the very first operand is activated.
    | When it is executed successfuly, next one is activated and so on.
    |
    | Below, `runWithInput` runs the given script and makes the given
    | input succeed (finish execution with success) in order it is given.
    | b, c, d, e, f are operands that won't be executed automaticaly after
    | activation, you need to pass them to `runWithInput` in order to
    | execute them.
    | `activatedShouldBe` asserts whether the given operands are activated
    | at the end of `runWithInput`. Fill the operands in there, separating
    | them with a comma. If no operands, in your opinion, are activated,
    | leave the parentheses empty: `activatedShouldBe()`.
    |
    | All the methods and operands described above are defined at KoanSuite
    | trait which this class extends and are not part of the SubScript
    | distribution."""
  ) {
    script s = b c d

    test(1) {
      runWithInput(s)(b, c)
      activatedShouldBe(__)
    }
  }

  koan(2)(
    """`;` is also a sequential operator, equivalent to space."""
  ) {
    script s = b; c; d

    test(1) {
      runWithInput(s)(b)
      activatedShouldBe(__)
    }
  }

  koan(3)(
    """Line break is also a sequential operator, equivalent to space.
    | Note that the indentation of all the script lines
    | must be greater or equal to that of the first line."""
  ) {
    script s = b
               c
               d

    test(1) {
      runWithInput(s)()
      activatedShouldBe(__)
    }
  }

}