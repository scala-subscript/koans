package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutAlternativeComposition extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | The Alternative operator `+` means exclusive choice:
    | actions may only happen in one of its operands;
    | the operator succeeds when any of its operands succeeds.
    |
    | When activated, the `+` activates its operands.
    | As soon as an atomic action happens in one of its operands,
    | all the others are excluded, so that their subgraphs are deactivated.
    |
    | Reminder: "thenActivatedOrSuccess(S)" means that
    | no actions are active, and the script may terminate successfully
    """
  ) {
    script s = b c + d e + f g

    test(1) {runWithInput(s)(   ); thenActivatedOrSuccess(___)}
    test(2) {runWithInput(s)( b ); thenActivatedOrSuccess(___)}
    test(3) {runWithInput(s)(___); thenActivatedOrSuccess( e )}
    test(4) {runWithInput(s)(___); thenActivatedOrSuccess( S )}
  }
  koan(2)(
      """
      | Like in math, the priority of the multiplication operator as whitespece (or even less!)
      | is higher than the priority of `+`.
      | In math you can parentheses to group subexpressions, as in '(1+2)*3'.
      | SubScript offers brackets for that purpose, as in `[x+y]z`
      |
      | However, often you don't need brackets when you specify a sequence with at least 1 choice in it,
      | thanks to the two other low priority operators ';' and the new line that is inferred as a kind of sequence.
      |
      | The following 3 scripts are therefore equivalent:
      """
  ) {
    script s0 = [ b c + d e ] h
    script s1 =   b c + d e ; h

    script s2 =   b c + d e
                  h

    test(1) {runWithInput(s1)(___); thenActivatedOrSuccess( h )}
  }
}
