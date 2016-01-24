package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.vm.ScriptNode

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutDataflow extends KoanSuite {

  koan("""Dataflow operator is used to take the result of a
        | left-hand side script and use it in the right-hand side
        | script.""") {
    script..
      s = a ~~(x: Int)~~> ^(x + 2)
      a = ^3

    runScript(s).$ shouldBe __
  }

  koan("""Dataflow can handle exceptions that occur in the left-hand
        | side script, just like `catch` in try-catch.""") {
    script..
      s(lhs: ScriptNode[Any]) =
        lhs ~~(x: Int)~~> ^(x + 2)
          +~/~(e: RuntimeException)~~> ^"Exception happened!"

      s1 = ^3
      s2 = {!throw new RuntimeException!}

    runScript(s(s1)).$ shouldBe __
    runScript(s(s2)).$ shouldBe __
  }

  koan("""Dataflow can have any number of result-matching clauses, just
        | like `match` statement.""") {
    script..
      s(lhs: ScriptNode[Any]) =
        lhs ~~(x: Int   )~~> ^(x + 2)
           +~~(x: String)~~> ^("Result: " + x)
           +~~(x: Double)~~> ^(x * 2)

      s1 = ^1
      s2 = ^"foo"
      s3 = ^2.2

    runScript(s(s1)).$ shouldBe __
    runScript(s(s2)).$ shouldBe __
    runScript(s(s3)).$ shouldBe __
  }

  koan("""Dataflow map is like dataflow, but its right-hand side
        | is a pure Scala expression. Its result becomes the result
        | of the parent script""") {
    script s = {!1!} ~~(x: Int)~~^ x * 2

    runScript(s).$ shouldBe Success(2)
  }

  koan("""Dataflow map can also have multiple matching clauses""") {
    script s(lhs: ScriptNode[Any]) =
      lhs ~~(x: Int   )~~^ x * 2
         +~~(x: Double)~~^ x * 3

    runScript(s([^2  ])).$ shouldBe __
    runScript(s([^2.0])).$ shouldBe __
  }

  koan("""Dataflow map has a shorthand version. Its right-hand side must
        | be a function from whatever left-hand side returns to whatever
        | you want the script's result to be.""") {
    def int2string(x: Int): String = x.toString
    script s = {!1!} ~~^ int2string

    runScript(s).$ shouldBe __
  }

}