package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.vm.ScriptNode

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutDataflow extends KoanSuite {

  koan(1)(
    """Dataflow operator is used to take the result of a
    | left-hand side script and use it in the right-hand side
    | script."""
  ) {
    script..
      s = a ~~(x: Int)~~> ^(x + 2)
      a = ^3

    test(1) {
      runScript(s).$ shouldBe Success(__`5`)
    }
  }

  koan(2)(
    """Dataflow can handle exceptions that occur in the left-hand
    | side script, just like `catch` in try-catch."""
  ) {
    script..
      s(lhs: ScriptNode[Any]) =
        lhs ~~(x: Int)~~> ^(x + 2)
          +~/~(e: RuntimeException)~~> ^"Exception happened!"

      s1 = ^3
      s2 = {!throw new RuntimeException!}

    test(1) {
      runScript(s(s1)).$ shouldBe Success(__`5`)
    }

    test(2) {
      runScript(s(s2)).$ shouldBe Success(__`"Exception happened!"`)
    }
  }

  koan(3)(
    """Dataflow can have any number of result-matching clauses, just
    | like `match` statement."""
  ) {
    script..
      s(lhs: ScriptNode[Any]) =
        lhs ~~(x: Int   )~~> ^(x + 2)
           +~~(x: String)~~> ^("Result: " + x)
           +~~(x: Double)~~> ^(x * 2)

      s1 = ^1
      s2 = ^"foo"
      s3 = ^2.2

    test(1) {
      runScript(s(s1)).$ shouldBe Success(__`3`)
    }

    test(2) {
      runScript(s(s2)).$ shouldBe Success(__`"Result: foo"`)
    }

    test(3) {
      runScript(s(s3)).$ shouldBe Success(__`4.4`)
    }
  }

  koan(4)(
    """Dataflow map is like dataflow, but its right-hand side
    | is a pure Scala expression. Its result becomes the result
    | of the parent script"""
  ) {
    script s = {!1!} ~~(x: Int)~~^ x * 2

    test(1) {
      runScript(s).$ shouldBe Success(__`2`)
    }
  }

  koan(5)(
    """Dataflow map can also have multiple matching clauses"""
  ) {
    script s(lhs: ScriptNode[Any]) =
      lhs ~~(x: Int   )~~^ x * 2
         +~~(x: Double)~~^ x * 3

    test(1) {
      runScript(s([^2  ])).$ shouldBe Success(__`4`)
    }

    test(2) {
      runScript(s([^2.0])).$ shouldBe Success(__`6.0`)
    }
  }

  koan(6)(
    """Dataflow map has a shorthand version. Its right-hand side must
    | be a function from whatever left-hand side returns to whatever
    | you want the script's result to be."""
  ) {
    def int2string(x: Int): String = x.toString
    script s = {!1!} ~~^ int2string

    test(1) {
      runScript(s).$ shouldBe Success(__`"1"`)
    }  
  }

}
