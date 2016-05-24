package subscript.koans

import subscript.language
import subscript.Predef._
import subscript.vm.Script

import subscript.koans.util.KoanSuite

class AboutAdvancedSyntax extends KoanSuite {

  koan(1)(
    """
    | You can use an inline definition of scripts from Scala code.
    | Just enclose a script expression in reactangular brackets, and use it as a Scala value.
    """
  ) {

    var a = 0
    val s = [let a=3]

    test(1) {runScript(s)        ; a shouldBe __`3`}
    test(1) {runScript([let a=4]); a shouldBe __`4`}
  }

  koan(2)(
    """
    | The type of an inline script is `Script[T]` where `T` is the so called "result type".
    | For now this result type is `Any`.
    | With this you can make your own control constructs:
    """
  ) {

    var i = 0
    var j = ""

    script..

      ifZeroThenDoElseDo(i:Int, thenPart: Script[Any], elsePart: Script[Any]) = if i==0 then thenPart
                                                                                        else elsePart

      s = ifZeroThenDoElseDo(i, [let j="0"], [let j="1"])

    test(1) {i=0; runScript(s); j shouldBe __`"0"`}
    test(2) {i=1; runScript(s); j shouldBe __`"1"`}
  }

  koan(3)(
    """
    | You can often give a script call with parameters a better look by getting rid of the
    | parentheses that enclose the parameter list. Instead you will use a colon.
    | So instead of `s(1)` you may write `s:1`.
    |
    | You can also split up the name of the called script, in case there are multiple parameters.
    | Instead of `sT(1,2)` you may write `s:1, t:2`
    | The separate parts are then glued together with camelCasing.
    |
    | SubScript's colon notation looks a bit like Scala's named parameters, but it seems to be a bit more convenient.
    | It looks more much like Smalltalk's method call syntax; only SubScript still employs a comma separator
    """
  ) {

    var i = 0
    var j = ""

    script..

      ifZeroThenDoElseDo(i:Int, thenPart: Script[Any], elsePart: Script[Any]) = if i==0 then thenPart
                                                                                        else elsePart

      s = ifZero: i, thenDo: [let j="0"], elseDo:[let j="1"]

    test(1) {i=0; runScript(s); j shouldBe __`"0"`}
    test(2) {i=1; runScript(s); j shouldBe __`"1"`}
  }

  koan(4)(
    """
    | A simple script call may also be used where a Scala value is expected.
    | Then that value has type `Script[T]` for some `T`.
    | So we can omit rectangular brackets in some cases; that makes the code slightly more efficient
    |
    """
  ) {

    var i = 0
    var j = ""

    script..

      ifZeroThenDoElseDo(i:Int, thenPart: Script[Any], elsePart: Script[Any]) = if i==0 then thenPart
                                                                                        else elsePart

      j0 = let j="0"
      j1 = let j="1"

      s1 = ifZero: i, thenDo: [j0], elseDo: [j1]
      s2 = ifZero: i, thenDo:  j0,  elseDo:  j1

    test(1) {i=0; runScript(s1); j shouldBe __`"0"`}
    test(2) {i=1; runScript(s2); j shouldBe __`"1"`}
  }

  koan(5)(
    """
    | We have seen various kinds of script expression terms:
    |
    | `break` `break?` `..?` `...` `while`
    | `[+]` `[-]` `[]` `[ expression ]`
    | `{!  !}` `{  **}` `{:  :}` `{.  .}` `{...  ...}`
    | script calls
    |
    | The script calls look like Scala method calls; a special colon syntax is allowed.
    |
    | Now you also may call normal Scala methods that way, even with the colon syntax.
    | And you may even have other Scala expression terms, that is, expressions without
    | prefix, infix and postfix calls (such as `!x`, `x+y`, `1 to 10`).
    |
    | So the following are also supported:
    |
    | - Scala literals (`1`, `2.0`, `'c'`, `"s"`, `true`, `false`)
    | - Scala expressions between parentheses, such as `(a+2*b)`
    | - statement sequences between braces, such as `{t;u}`
    | - variables and values
    |
    | But what does it all mean? That depends on the type of such a Scala expression:
    |
    | - case Script[T] => a script call
    | - case Unit      => a method call in a tiny code fragment
    | - else there should be an implicit conversion in scope to one of the previous 2 types; that conversion applies
    |
    | An implicit conversion to type Script is possible using an `implicit script`.
    |
    | This way SubScript rivals the conciseness of grammar description languages such as BNF and YACC.
    """
  ) {

      var a0, a1, a2, a3: Any = null

      implicit def  int2set_a0(i: Int) = a0 = i
      implicit def char2set_a1(c: Char) = a1 = c

      implicit script..

        boolean2set_a2(b: Boolean) = {:a2=b:}
         string2set_a3(s: String ) = {!a3=s!}

      script..

      //s = 1 'c' true "ok" TBD; implicit conversion to Unit in scripts not yet supported

        s = true "ok"

//      test(1) {runScript(s); a0 shouldBe __`1`}   TBD
//      test(2) {runScript(s); a1 shouldBe __`'c'`} TBD
      test(3) {runScript(s); a2 shouldBe __`true`}
      test(4) {runScript(s); a3 shouldBe __`"ok"`}
  }
}
