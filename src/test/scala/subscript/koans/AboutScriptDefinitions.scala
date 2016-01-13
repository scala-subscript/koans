package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutScriptDefinitions extends KoanSuite {
  
  koan("""Scripts' signatures are defined with same syntax as
        | ones of methods.""") {
    var i = 0
    script s(x: Int) = {!i = x!}
    runScript(s(10))
    i shouldBe __

    script s2(x: Int, y: Int) = {!i = x + y!}
    runScript(s2(10, 20))
    i shouldBe __

    script s3(x: Int)(y: Int) = {!i = x * y!}
    runScript(s3(2)(3))
    i shouldBe __
  }

  koan("""Scripts can have return types. So far, SubScript is untyped,
        | so they all must be Any. More on script results in later koans.""") {
    var i = 0
    script s: Any = {!i = 10!}
    runScript(s)

    i shouldBe __
  }

  koan("""Scripts can be members of classes, objects and traits.
        | They can be treated like ordinary methods. Abstract scripts
        | must have return type specified.""") {
    abstract class Foo {
      var i = 0
      script foo = bar
      protected script bar: Any
    }

    class Bar extends Foo {
      override script bar = {!i = 10!}
    }

    class FooBar extends Foo {
      override script bar = {!i = 20!}
    }

    val bar = new Bar
    runScript(bar.foo)
    bar.i shouldBe __

    val foobar = new FooBar
    runScript(foobar.foo)
    foobar.i shouldBe __
  }

  koan("""Scripts can be defined using a shorthand syntax.
        | After `script..`, you can define scripts without `script`
        | keyword. Indentation matters: if the line's indentation is
        | less than that of of the first script definition, it will
        | signify the end of shorthand syntax.""") {
    var i = 0

    script..
      s1 = {!i += 1!}
      s2 = s1 s1
      s3 = s2 s2

    runScript(s3)

    i shouldBe __
  }

}