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

    test(1) {runWithInput(s1( true))(); thenActivatedOrSuccess(___`b`)}
    test(2) {runWithInput(s1(false))(); thenActivatedOrSuccess(___`S`)}
    test(3) {runWithInput(s2( true))(); thenActivatedOrSuccess(___`b`)}
    test(4) {runWithInput(s2(false))(); thenActivatedOrSuccess(___`c`)}
  }

  koan(2)(
    """
    | The `do-then-else` operators is similar to `if`, but its first part is a SubScript
    | expression rather than a Scala expression.
    | Also the `then` part is optional; there should only be
    | at least one of `then` or `else`. Like with `if-else`, an absent `then`
    | or `else` part is as if the part was present with a neutral process `[]`.
    |
    | Upon activation the `do` part is activated.
    | Each time the `do` part succeeds the `then` part is activated.
    | So with the `do` and `then` parts this operator acts much like sequential composition.
    | Differences are that sequential composition may have more than two operands,
    | and it may become an iteration, whereas `do`-`then` cannot iterate.
    |
    | When the `do` part ends in failure the `else` part is actvated.
    |
    | When the `then` part or the `else` part succeeds, the entire `do-then-else` operator succeeds.
    """
  ) {
    script..
      st0  = do b [-] then c
      st1  = do b     then c
      ste0 = do b [-] then c else d
      ste1 = do b     then c else d
      se0  = do b [-]        else d
      se1  = do b            else d

    test(1) {runWithInput(st0 )(b); thenActivatedOrSuccess(___`S`)}
    test(2) {runWithInput(st1 )(b); thenActivatedOrSuccess(___`c`)}
    test(3) {runWithInput(ste0)(b); thenActivatedOrSuccess(___`d`)}
    test(4) {runWithInput(ste1)(b); thenActivatedOrSuccess(___`c`)}
    test(5) {runWithInput(se0 )(b); thenActivatedOrSuccess(___`d`)}
    test(6) {runWithInput(se1 )(b); thenActivatedOrSuccess(___`S`)}
  }

}
