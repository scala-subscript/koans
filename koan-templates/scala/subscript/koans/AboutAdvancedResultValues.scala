package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutAdvancedResultValues extends KoanSuite {

  koan(1)(
    """
    | When results become available in a loop, there is the
    | possibility to collect those all.
    |
    | A double caret `^^` after an operand makes a result of a script
    | to be `List[T]` instead of `T` (wrapped in a `Try` of course).
    | Here T is the result type of the operand; for the time being
    | it is taken as Any, until the SubScript compilation is improved
    | in this respect.
    |
    | Each success of this operand will record its result to that
    | `List`. The index in the list of the recorded result will be equal
    | to the `pass` attribute of the operand.
    |
    | The list will grow just enough to accomodate all results that need to be recorded.
    """
  ) {
    script s = var i = 0; while(i<3) {!i!}^^ {!i += 1!}

    test(1) {runScript(s).$ shouldBe Success(List(__`0,1,2`))}
  }

  koan(2)(
    """
    | The remark in the previous koan about the index of the
    | result value in the list is important.
    """
  ) {
    script s = var i = 0
               while(i<4) {!i!}^^ {!i += 1!}
               let i = 0
               while(i<3) {!i += 1!} {!i!}^^

    test(1) {runScript(s).$ shouldBe Success(__`List(1,2,3,3)`)}
  }

  koan(3)(
    """
    | It is also easy to record multiple values.
    |
    | A double caret followed by an positive integer number (`^^1`, `^^2` etc)
    | causes the result of the enclosing script to be a tuple;
    | it places the result of that operand to the specified position in the tuple.
    """
  ) {
    script s1 = {!1!}^^1 {!2!}^^2
    script s2 = {!1!}^^2 {!2!}^^1

    test(1) {runScript(s1).$ shouldBe Success(__`(1,2)`)}
    test(2) {runScript(s2).$ shouldBe Success(__`(2,1)`)}
  }

  koan(4)(
    """
    | You can even create lists of tuples concisely
    """
  ) {
    script..
      s = var i= 0
          var j=10
          while(i<3) [^i^^1 ^j^^2]^^  {! i+=1; j-=1 !}

    test(1) {runScript(s).$ shouldBe Success(List(__`(0,10),(1,9),(2,8)`))}
  }

}
