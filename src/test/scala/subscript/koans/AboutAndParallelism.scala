package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutAndParallelism extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | The normal parallel operator `&` is also called and-parallel.
    | It executes all its operands in a parallel way.
    | The code fragments such as `{! .... !}` will normally not be executed
    | concurrently, but in an interleaved way: one after another,
    | a bit like you can shuffle merge a card deck.
    |
    | `&` succeeds if and only if all its children have are succeeding.
    |
    | For most operators Scala's precedence rules apply.
    | Therefore `a b & c + d` is equal to [[a b] & c] d.
    |
    | In the following koan, activation of the call to `f` at the end is meant
    | to signal whether the preceding `&` operator has success
    | """
  ) {
    script s = [b c & d e] f

    test(1) {runWithInput(s)(       ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(b      ); activatedShouldBe(__)}
    test(3) {runWithInput(s)(b,c    ); activatedShouldBe(__)}
    test(4) {runWithInput(s)(b,c,d,e); activatedShouldBe(__)}
  }

  koan(2)(
    """
    | As soon as one of its children has had a failure, i.e. was deactivated
    | without having success, then `&` cannot succeed any more.
    """
  ) {
    script s = [b c & d [-]] f

    test(1) {runWithInput(s)(       ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(      d); activatedShouldBe(__)}
    test(3) {runWithInput(s)(b,    d); activatedShouldBe(__)}
    test(4) {runWithInput(s)(b, c, d); activatedShouldBe(__)}
  }

  koan(3)(
    """
    | `&&` is the so-called 'strong-and-parallel' operator.
    | This is much like `&`, except that whenever one of the
    | operands terminates in failure (i.e. without success)
    | then all other operands are excluded (terminated).
    |
    | It is comparable to the boolean operator `&&`, which
    | is also a stronger version of `&`, in the sense that
    | it stops evaluation when one operand (the left hand side)
    | evaluates false.
    |
    """
  ) {
    script s = [b c && d [-]] f

    test(1) {runWithInput(s)(       ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(      d); activatedShouldBe(__)}
    test(3) {runWithInput(s)(b,    d); activatedShouldBe(__)}
    test(4) {runWithInput(s)(b, c, d); activatedShouldBe(__)}
  }
}