package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutConditionalOperators extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | The `if` operator works largely as you would expect.
    | The condition does not need to be inside parentheses;
    | instead there is a `then` keyword. This may become
    | standard in a future version of Scala BTW.
    |
    | The `else` part is optional, as usual. In case there
    | is no `else` part and if the condition evaluates to
    | `false` upon activation, then the `if` operator
    | acts as if it is the neutral process `[]`.
    """
  ) {
    script s1(predicate: Boolean) = if predicate then b
    script s2(predicate: Boolean) = if predicate then b else c

    test(1) {runWithInput(s1( true))(); activatedShouldBe(___)}
    test(2) {runWithInput(s1(false))(); activatedShouldBe(___)}
    test(3) {runWithInput(s2( true))(); activatedShouldBe(___)}
    test(4) {runWithInput(s2(false))(); activatedShouldBe(___)}
  }

  koan(2)(
    """
    | The `do-then-else` operators is similar to `if`, but its first part is a SubScript
    | expression rather than a Scala expression.
    | Also the `then` part is optional; there should only be
    | at least one of `then` or `else`.
    |
    | Upon activation the `do` part is activated.
    | Each time the `do` part succeeds the `then` part is activated.
    | So with the `do` and `then` parts this operator acts much like sequential composition.
    | Differences are that sequential composition may have more than two operands,
    | and it may become an iteration, whereas `do`-`then` cannot iterate.
    |
    | When the `do` part ends in failure the `else` part is actvated.
    |
    | When the `then` part or the `else` part succeed, the entire `do-then-else` operator succeeds.
    | `then` part is executed; if it ends without success - `else` part.
    """
  ) {
    script..
      st0  = do b [-] then c
      st1  = do b     then c
      ste0 = do b [-] then c else d
      ste1 = do b     then c else d
      se0  = do b [-]        else d
      se1  = do b            else d

    test(1) {runWithInput(st0 )(b); activatedShouldBe(___)}
    test(2) {runWithInput(st1 )(b); activatedShouldBe(___)}
    test(3) {runWithInput(ste0)(b); activatedShouldBe(___)}
    test(4) {runWithInput(ste1)(b); activatedShouldBe(___)}
    test(5) {runWithInput(se0 )(b); activatedShouldBe(___)}
    test(6) {runWithInput(se1 )(b); activatedShouldBe(___)}
  }

}