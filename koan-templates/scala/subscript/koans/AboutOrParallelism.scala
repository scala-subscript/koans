package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutOrParallelism extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | Next to the two and-parallel operators `&` and `&&`
    | there are two or-parallel operators `|` and `||`.
    | These are in a way analogous to the boolean operators
    | with the same symbols.
    |
    | Of these four `|` is barely used. It acts much like `&`,
    | only it has success when any of its operands has success,
    | rather than each of its operands for `&`
    |
    | Reminder: specify an 'S' when the script has success!
    """
  ) {
    script s = b c | d e

    test(1) {runWithInput(s)(       ); thenActivatedOrSuccess(__`b,d`)}
    test(2) {runWithInput(s)(b      ); thenActivatedOrSuccess(__`c,d`)}
    test(3) {runWithInput(s)(b,c    ); thenActivatedOrSuccess(__`S,d`)}
    test(4) {runWithInput(s)(b,c,d  ); thenActivatedOrSuccess(__`S,e`)}
    test(5) {runWithInput(s)(b,c,d,e); thenActivatedOrSuccess(__`S`)}
  }

  koan(2)(
    """
    | To turn a process in something that succeeds 'at all moments'
    | set it or-parallel to the empty process
    |
    """
  ) {
    script s =  [+] | b c d

    test(1) {runWithInput(s)(     ); thenActivatedOrSuccess(__`b,S`)}
    test(2) {runWithInput(s)(b    ); thenActivatedOrSuccess(__`c,S`)}
    test(3) {runWithInput(s)(b,c  ); thenActivatedOrSuccess(__`d,S`)}
    test(4) {runWithInput(s)(b,c,d); thenActivatedOrSuccess(__`S`)}
  }

  koan(3)(
    """
    |
    | `||` is the so-called 'strong-and-parallel' operator.
    | It acts much like `|`, except that whenever one of the
    | operands terminates with a success
    | then all other operands are excluded (terminated).
    |
    | `||` seems to be more often used than `&`.
    | It is often very handy to let a processes terminate as soon
    | as a parallel process terminates successfully.
    """
  ) {
    script s1 = b c   || d
    script s2 = b [-] || d

    test( 1) {runWithInput(s1)(     ); thenActivatedOrSuccess(__`b,d`)}
    test( 2) {runWithInput(s1)(b    ); thenActivatedOrSuccess(__`c,d`)}
    test( 3) {runWithInput(s1)(b,  d); thenActivatedOrSuccess(__`S`)}
    test( 4) {runWithInput(s1)(b,c  ); thenActivatedOrSuccess(__`S`)}
    test( 5) {runWithInput(s1)(    d); thenActivatedOrSuccess(__`S`)}

    test( 6) {runWithInput(s2)(     ); thenActivatedOrSuccess(__`b,d`)}
    test( 7) {runWithInput(s2)(b    ); thenActivatedOrSuccess(__`d`)}
    test( 8) {runWithInput(s2)(  d  ); thenActivatedOrSuccess(__`S`)}
  }
}
