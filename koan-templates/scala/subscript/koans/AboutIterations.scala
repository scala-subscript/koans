package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutIterations extends KoanSuite with OperatorKoansHelper {

  koan(1)(
    """
    | These terms support iterations: `while`, `break`, `break?`, `..?`, `...`.
    | They are not atomic actions; in the ACP sense they act much like
    | the neutral process. However they control the behaviour of the
    | context, i.e. the enclosing behavioural composition such as sequence or parallelism.
    |
    | In sequences, `break` and 'while` behave much like their Scala counterparts.
    |
    | `break` in a sequence forces an exit from that sequence.
    |
    | `while` is followed by a condition; this may be a simple Scala expression,
    | not necessarily enclosed in parentheses.
    |
    | `...` is equivalent to `while true`. It turns the parent expression with an
    | operator such as `;` into an iteration.
    |
    | `while cond` has the combined functionality of `if cond then break` and `...`.
    |
    | `while` and `...` do not need to occur at the start of a script expression.
    """
  ) {
    var i1 = 0;
    var i2 = 0;
    var i3 = 0;
    var i4 = 0;
    var i5 = 0;
    var i6 = 0;

    script..
      s1 = ...; if i1>2 then break; {! i1+=1 !}
      s2 = ...; if i2>2 then break; {! i2+=1 !}; ...

      s3 =  while (i3<=2) {! i3+=1 !}
      s4 =  while (i4<=2) {! i4+=1 !} ...
      s5 =                {! i5+=1 !} while (i5<=2)

      s6 =  {! i6+=1 !} while (i6<=3) {! i6+=1 !}

    test(1) {runScript(s1); i1 shouldBe __`3`}
    test(2) {runScript(s2); i2 shouldBe __`3`}
    test(3) {runScript(s3); i3 shouldBe __`3`}
    test(4) {runScript(s4); i4 shouldBe __`3`}
    test(5) {runScript(s5); i5 shouldBe __`3`}
    test(6) {runScript(s6); i6 shouldBe __`5`}
  }

  koan(2)(
    """
    | The iteration constructs are not limited for use in a sequential composition.
    |
    | When applied as an operand of a `+`, `&`, `&&`, `|`, `||`, `/`,
    | `...` and `while` will repeat the activation of operands after the last one.
    |
    | `while` may break the activation depending on the condition.
    | `break` breaks the activation of operands.
    |
    | A loop counter is available in scripts: the value `pass`.
    | It starts at 0. For other terms the `pass` value keeps the value
    | that they had during their activation.
    """
  ) {
    var i0 = 0;
    var i1 = 0;
    var i2 = 0;
    var i3 = 0;
    var i4 = 0;
    var i5 = 0;

    script..
      s1 = ... & [if pass >2 then break] & {! i1+=1 !}
      s2 =    while (pass<=2)            & {! i2+=1 !}
      s3 =    while (pass<=2)            & {! i3+=pass !}
      s4 =    while (pass<=2)            & {! i0+=1 !} {! i4+=i0 !}
      s5 =    while (pass<=2)           || {! i5+=1 !}

    test(1) {runScript(s1); i1 shouldBe __`3`}
    test(2) {runScript(s2); i2 shouldBe __`3`}
    test(3) {runScript(s3); i3 shouldBe __`3`}
    test(4) {runScript(s4); i4 shouldBe __`9`}
    test(5) {runScript(s5); i5 shouldBe __`1`}
  }

  koan(3)(
    """
    | Iteration terms may be refined, e.g. using `pass`.
    """
  ) {
    var i1 = 0;
    var i2 = 0;
    var i3 = 0;
    var i4 = 0;

    script times(n:Int) = while(pass<n)

    script..
      s1 = times(2) {! i1+=1 !}
      s2 =          {! i2+=1 !} times(2)

    test(1) {runScript(s1); i1 shouldBe __`2`}
    test(2) {runScript(s2); i2 shouldBe __`3`}
  }

  koan(4)(
    """
    | `..?` denotes an loop, and at the same time an optional exit point.
    | It combines the functionality of `...` and `break?`.
    |
    | `break?` is called "optional break", not necessarily from a loop.
    | In a sequential context it makes what comes next optional;
    | thus is much like adding the empty process to what comes next:
    |
    | `a break? c` behaves the same as `a; c+[+]
    |
    | In the context of `&`, `&&`, `|`, `||`, `/`  `break?`
    | will not break the activation. To determine success of such an operator,
    | the parts after a `break?` are not considered, except when at least
    | one atomic action has happened there.
    """
  ) {
    script..
      s1 =   break? b; c
      s2 = b break? c
      s3 = b ..? c
      s4 = b ..?
      s5 =   ..? b

    test( 1) {runWithInput(s1)(     ); thenActivatedOrSuccess(__`b,c`)}
    test( 2) {runWithInput(s1)(b    ); thenActivatedOrSuccess(__`c`)}
    test( 3) {runWithInput(s1)(c    ); thenActivatedOrSuccess(__`S`)}
    test( 4) {runWithInput(s1)(b,c  ); thenActivatedOrSuccess(__`S`)}

    test( 5) {runWithInput(s2)(     ); thenActivatedOrSuccess(__`b`)}
    test( 6) {runWithInput(s2)(b    ); thenActivatedOrSuccess(__`c,S`)}
    test( 7) {runWithInput(s2)(b,c  ); thenActivatedOrSuccess(__`S`)}

    test( 8) {runWithInput(s3)(     ); thenActivatedOrSuccess(__`b`)}
    test( 9) {runWithInput(s3)(b    ); thenActivatedOrSuccess(__`c,S`)}
    test(10) {runWithInput(s3)(b,c  ); thenActivatedOrSuccess(__`b`)}
    test(11) {runWithInput(s3)(b,c,b); thenActivatedOrSuccess(__`c,S`)}

    test(12) {runWithInput(s4)(     ); thenActivatedOrSuccess(__`b`)}
    test(13) {runWithInput(s4)(b    ); thenActivatedOrSuccess(__`b,S`)}
    test(14) {runWithInput(s4)(b,b  ); thenActivatedOrSuccess(__`b,S`)}

    test(15) {runWithInput(s5)(     ); thenActivatedOrSuccess(__`b,S`)}
    test(16) {runWithInput(s5)(b    ); thenActivatedOrSuccess(__`b,S`)}
    test(17) {runWithInput(s5)(b,b  ); thenActivatedOrSuccess(__`b,S`)}

  }

  koan(5)(
    """
    | When a `&`, `&&`, `|`, `||`, or `/` is activated,
    | the operands are activated.
    | The first time a `break?` is activated there
    | will not break the activation chain, but a next time will.
    |
    | The operands activated in two such `break?` instances are
    | at first considered to be optional processes. They become
    | "normal" as soon as an atomic action happens at one of them.
    | Around that moment activation of the chain continues, etc.
    |
    | To determine success of such an operator, the optional operands
    | are not considered.
    |
    | This mechanism is for instance useful if you want to model a process that
    | can disrupt itself, to start over again.
    | This happens (or should happen) when you are typing in the URL box
    | of a modern web browser: each time it will start looking up matching
    | phrases, which may take some time. If you type again a character
    | while such a lookup is ongoing, that lookup should be canceled,
    | and a new lookup should start.
    |
    | `break?` makes probably no sense in context of `+`. It will
    | break the activation in case no code fragments with atomic actions had been activated since
    | the previous `break?` or, if there was none, since the first operand activation
    |
    """
  ) {
    script..
      s = b c d / ..?

    test(1) {runWithInput(s)(           ); thenActivatedOrSuccess(__`b`)}
    test(2) {runWithInput(s)(b          ); thenActivatedOrSuccess(__`b,c`)}
    test(3) {runWithInput(s)(b,b        ); thenActivatedOrSuccess(__`b,c`)}
    test(4) {runWithInput(s)(b,b,c      ); thenActivatedOrSuccess(__`b,d`)}
    test(5) {runWithInput(s)(b,c,b      ); thenActivatedOrSuccess(__`b,c`)}
    test(6) {runWithInput(s)(b,c,b,b,c  ); thenActivatedOrSuccess(__`b,d`)}
    test(7) {runWithInput(s)(b,c,b,b,c,d); thenActivatedOrSuccess(__`S`)}
  }
/*
  koan(6)(
    """
    | `break?` makes probably no sense in context of `+`. It will
    | break the activation in case no code fragments with atomic actions had been activated since
    | the previous `break?` or, if there was none, since the first operand activation
    """
  ) {
    script..
      s1 = b + break? + c
      s2 = b + break? + [] + break? + c

    show(1) {runWithInput(s1)(); thenActivatedOrSuccess(__`b,c`)}
    show(2) {runWithInput(s2)(); thenActivatedOrSuccess(__`b`)}
  }
*/
}
