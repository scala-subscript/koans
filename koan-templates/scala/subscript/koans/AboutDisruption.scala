package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutDisruption extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | The Disruption operator `/` expresses that a process may be
    | disrupted by other ones. So whenever an action happens in
    | an operand, this disrupts all operands to the left.
    | The `+` operator does the same, but it also affects
    | operands to the right.
    |
    | Like `+`, `|` and `||`, the `/` operator is or-like:
    | it succeeds when any of its operands succeeds.
    |
    | When activated, the `+` activates its operands.
    | As soon as an atomic action happens in one of its operands,
    | all the others to the left are excluded, so that their subgraphs are deactivated.
    |
    | When an operand side terminates successfully the
    | operands more to the right are deactivated.
    """
  ) {
    script s = b c / d e

    test(1) {runWithInput(s)(   ); thenActivatedOrSuccess(___`b,d`)}
    test(2) {runWithInput(s)(b  ); thenActivatedOrSuccess(___`c,d`)}
    test(3) {runWithInput(s)(b,c); thenActivatedOrSuccess(___`S`)}
    test(4) {runWithInput(s)(d  ); thenActivatedOrSuccess(___`e`)}
    test(5) {runWithInput(s)(b,d); thenActivatedOrSuccess(___`e`)}
    test(6) {runWithInput(s)(d,e); thenActivatedOrSuccess(___`S`)}
  }

}
