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
    i shouldBe 10

    script s2(x: Int, y: Int) = {!i = x + y!}
    runScript(s2(10, 20))
    i shouldBe 30

    script s3(x: Int)(y: Int) = {!i = x * y!}
    runScript(s3(2)(3))
    i shouldBe 6
  }

  koan("""Scripts can have return types. So far, SubScript is untyped,
        | so they all must be Any.""") {
    var i = 0
    script s: Any = {!i = 10!}
    runScript(s)

    i shouldBe 10
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
    bar.i shouldBe 10

    val foobar = new FooBar
    runScript(foobar.foo)
    foobar.i shouldBe 20
  }

  koan("""Scripts can be defined using a shorthand syntax.
        | Indentation matters: the shorthand syntax breaks back
        | to the ordinary Scala code when the indentation of the line
        | is smaller then the one of the previous script's body.""") {
    var i = 0

    script..
      s1 = {!i += 1!}
      s2 = s1 s1
      s3 = s2 s2

    runScript(s3)

    i shouldBe 4
  }

}