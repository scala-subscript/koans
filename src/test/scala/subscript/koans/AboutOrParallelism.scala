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
    """
  ) {
    script s = [b c | d e] f

    test(1) {runWithInput(s)(       ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(b      ); activatedShouldBe(__)}
    test(3) {runWithInput(s)(b,c    ); activatedShouldBe(__)}
    test(4) {runWithInput(s)(b,c,d  ); activatedShouldBe(__)}
    test(5) {runWithInput(s)(b,c,d,e); activatedShouldBe(__)}
  }

  koan(2)(
    """
    | When at least one of the children has a success,
    | the `|` succeeds as well. This occurs as well each time when
    | an atomic action happens in another operand
    |
    """
  ) {
    script s = [b | [ [+] + d e ] ] f

    test(1) {runWithInput(s)(     ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(b    ); activatedShouldBe(__)}
    test(3) {runWithInput(s)(  d  ); activatedShouldBe(__)}
    test(4) {runWithInput(s)(  d,e); activatedShouldBe(__)}
    test(5) {runWithInput(s)(b,d  ); activatedShouldBe(__)}
    test(6) {runWithInput(s)(b,d,e); activatedShouldBe(__)}
  }

  koan(3)(
    """
    | To turn a process in something that succeeds 'at all moments'
    | set it or-parallel to the empty process
    |
    """
  ) {
    script s = [ [+] | b c d ] f

    test(1) {runWithInput(s)(     ); activatedShouldBe(__)}
    test(2) {runWithInput(s)(b    ); activatedShouldBe(__)}
    test(3) {runWithInput(s)(b,c  ); activatedShouldBe(__)}
    test(4) {runWithInput(s)(b,c,d); activatedShouldBe(__)}
  }

  koan(4)(
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
    script s1 = [b c ||        d   ] f
    script s2 = [b   || [[+] + d e]] f

    test( 1) {runWithInput(s1)(     ); activatedShouldBe(__)}
    test( 2) {runWithInput(s1)(b    ); activatedShouldBe(__)}
    test( 3) {runWithInput(s1)(b,  d); activatedShouldBe(__)}
    test( 4) {runWithInput(s1)(b,c,d); activatedShouldBe(__)}
    test( 5) {runWithInput(s1)(    d); activatedShouldBe(__)}

    test( 6) {runWithInput(s2)(     ); activatedShouldBe(__)}
    test( 7) {runWithInput(s2)(b    ); activatedShouldBe(__)}
    test( 8) {runWithInput(s2)(  d  ); activatedShouldBe(__)}
    test( 9) {runWithInput(s2)(  d,b); activatedShouldBe(__)}
    test(10) {runWithInput(s2)(  d,e); activatedShouldBe(__)}
  }
}