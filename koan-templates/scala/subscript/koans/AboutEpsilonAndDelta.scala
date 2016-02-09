package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutEpsilonAndDelta extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | There are some special processes in SubScript, among which the following:
    |
    | [-] - the `deadlock process`
    | [+] - the    `empty process`
    |  [] - the  `neutral process`
    |
    | These execute no code and do not relate to an atomic action.
    |
    | The deadlock process is something that never can be done, and 'after' it nothing can happen.
    | The empty process is something that succeeds immediately.
    |
    | The neutral process `[]` behaves neutrally, i.e. like either `[-]` or `[+]`, depending on the context:
    | - in the context of a sequence (`;`), it behaves like [+]
    | - in the context of an exclusive choice (`+`), it behaves like [-]
    |
    | But more operators will be introduced later.
    | Some of these will be "logially and-like" (see below for the exact meaning);
    | for these the neutral process behaves like [+]; for the others it behaves like [-]
    |
    | Note that when a script may succeed, there should be an 'S' in the thenActivatedOrSuccess parameters.
    |
    | E.g. consider the script a = b + [+]
    | This may do a `b`, but it also succeeds because of the empty process `[+]`
    | Therefore the following will hold:
    |
    |    runWithInput(a)(); thenActivatedOrSuccess(b,S)
    |
    |
    """
  ) {
    script a0 = b + [-] + c
    script a1 = b + [+] + c
    script an = b + []  + c
    script s0 = b   [-]   c
    script s1 = b   [+]   c
    script sn = b   []    c

    test( 1) {runWithInput(a0)( ); thenActivatedOrSuccess(__`b,c`)}
    test( 2) {runWithInput(a1)( ); thenActivatedOrSuccess(__`b,c,S`)}
    test( 3) {runWithInput(an)( ); thenActivatedOrSuccess(__`b,c`)}
    test( 4) {runWithInput(a0)(b); thenActivatedOrSuccess(__`S`)}
    test( 5) {runWithInput(a1)(b); thenActivatedOrSuccess(__`S`)}
    test( 6) {runWithInput(an)(b); thenActivatedOrSuccess(__`S`)}
    test( 7) {runWithInput(s0)( ); thenActivatedOrSuccess(__`b`)}
    test( 8) {runWithInput(s1)( ); thenActivatedOrSuccess(__`b`)}
    test( 9) {runWithInput(sn)( ); thenActivatedOrSuccess(__`b`)}
    test(10) {runWithInput(s0)(b); thenActivatedOrSuccess(__` `)}
    test(11) {runWithInput(s1)(b); thenActivatedOrSuccess(__`c`)}
    test(12) {runWithInput(sn)(b); thenActivatedOrSuccess(__`c`)}

  }

  koan(2)(
    """
    | With the empty process you can express optional behaviour.
    |
    | Note that a process `[+]+b` may succeed twice:
    | once immediately after activation, and another time,
    | after `b` has happened. This is a weird property of SubScript
    | that you may need to get used to. But it is extremely powerful.
    """
  ) {
    script opt = [+]+b; c

    test(1) {runWithInput(opt)(   ); thenActivatedOrSuccess(__`b,c`)}
    test(2) {runWithInput(opt)(b  ); thenActivatedOrSuccess(__`c`)}
    test(3) {runWithInput(opt)(c  ); thenActivatedOrSuccess(__`S`)}
    test(4) {runWithInput(opt)(b,c); thenActivatedOrSuccess(__`S`)}
  }

  koan(3)(
    """
    | Discussion
    |
    | In the underlying theory for SubScript, ACP, the first two are also
    | known as `delta` and `epsilon`, and in later publications they
    | are referred to as 0 and 1.
    |
    | Writing `0` for `[-] and `1` for `[+]`, the following axioms apply:
    |
    |   0 + x = x
    |   1 * x = x
    |   x * 1 = x
    |   0 * x = 0
    |
    | where * is the multiplication symbol, for sequential composition.
    |
    |   x + 0 = x follows from the first axiom, in combination with the commutativity of `+`
    |
    | To see how the 1 expresses optional behavior we "compute" (1+x) * y:
    |
    |  (1+x) * y = 1*y + x*y     (because of the axiom (x+y)*z = x*z + y*z)
    |            =   y + x*y     (because of the axiom     1*x = x)
    |
    | So in any case y will happen, optionally preceded by x.
    |
    | Parser generator languages have usually a higher level construct with a
    | similar syntax, e.g., [x] y.
    | The pity is that you don't see there that optionality of a thing is basically a choice
    | between that thing and nothing.
    |
    | SubScript operators have been designed to be either and-like or or-like (with one exception).
    | That means for an or-like operator
    |
    |   [-] @  x = x
    |    x  @ [-]= x
    |
    | and for an and-like operator `@`:
    |
    |   [+] @  x = x
    |    x  @ [+]= x
    |
    | For all operators '@' (except for one) the following will hold:
    |
    |   [] @  x = x
    |    x  @ []= x
    |
    | An activated script expression may succeed multiple times.
    | Upon activation, if it had not succeeded after its last atomic action had happened,
    | then such deactivation is called a failure. In a way it ends as if it has the deadlock
    | process [-] at the end.
    |
    """
  ) {
  }

}
