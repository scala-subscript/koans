package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutScriptDefinitions extends KoanSuite {
  
  koan(1)(
    """
    | A script signature looks often much like a method signature.
    | They may even have multiple parameter lists, so that they allow for currying.
    """
  ) {
    var i = 0

    script s1(x: Int)         = {! i = x   !}
    script s2(x: Int, y: Int) = {! i = x+y !}
    script s3(x: Int)(y: Int) = {! i = x*y !}

    test(1) { runScript(s1(1)   ); i shouldBe __ }
    test(2) { runScript(s2(1, 2)); i shouldBe __ }
    test(3) { runScript(s3(2)(3)); i shouldBe __ }
  }

  koan(2)(
    """
    | Scripts may have result values; these are much like return values for methods,
    | later koans will give more details.
    | The type of the result value may be specified in the script signature,
    | just like for the return types of Scala methods.
    | For now the only supported result type is `Any`.
    | At the moment of writing there is work in progress to support all types.
    |
    | The koans on dataflow will show the use of script result values.
    """
  ) {
    var i = 0
    script s: Any = {! i = 1 !}
    
    test(1) { runScript(s); i shouldBe __ }
  }

  koan(3)(
    """
    | Like methods, scripts may be members of classes, objects and traits.
    | They may have modifiers such as `private`, `protected`, `abstract`, `override`.
    | Abstract scripts must have their result type specified.
    """
  ) {
    abstract class Foo {
      var i = 0
      script foo = bar
      protected script bar: Any
    }

    class Bar1 extends Foo { override script bar = {! i = 1 !} }
    class Bar2 extends Foo { override script bar = {! i = 2 !} }

    test(1) {val bar1 = new Bar1; runScript(bar1.foo); bar1.i shouldBe __ }
    test(2) {val bar2 = new Bar2; runScript(bar2.foo); bar2.i shouldBe __ }
  }

  koan(4)(
    """
    | There is a convenient shorthand syntax for scripts definitions.
    | After `script..`, you can define scripts without repeating the `script` keyword.
    | The whole is called a `scripts section`.
    |
    | Indentation matters for determining both the end of a script definition
    | and the end of the scripts section.
    |
    | Suppose in a top level script expression (i.e. not inside braces or parentheses etc.)
    | a line break occurs, and the next line does not start with an operator such as `;`,
    | then
    | - if the indentation of that line is less than that of the equals sign of the current
    |   script definition (just after the header),
    |   the script definition ends
    | - if the indentation of that line is less than that of the first script definition,
    |   the scripts section ends
    """
  ) {
    var i = 0

    script..
      s1 = {! i += 1 !}
      s2 = s1 s1
      s3 = s2 s2

    test(1) {runScript(s3); i shouldBe __ }
  }

}
