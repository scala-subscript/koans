package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.vm.Script

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutDataflow extends KoanSuite {

  koan(1)(
    """
    | The dataflow operator takes the result of a script at the
    | left-hand side and passes it on, like a parameter, to the right-hand side
    """
  ) {
    script s = ^3 ~~(x: Int)~~> ^(x+2)

    test(1) {runScript(s).$ shouldBe Success(__`5`)
    }
  }

  koan(2)(
    """
    | The dataflow construct may also carry exceptions that occur in
    | the left hand side, just like `catch` in try-catch.
    | In the arrow part `+~/~`:
    | - the `+` indicates an alternative route
    | - the `/` indicates this route is taken in case of unsuccessful termination
    |           of the left hand side, which will often be due to an exception
    """
  ) {
    script..
      s(lhs: Script[Any]) = lhs ~~(x: Int)~~> ^(x + 2)
                                  +~/~(e: RuntimeException)~~> ^"Exception happened!"

      s1 = ^3
      s2 = {!throw new RuntimeException!}

    test(1) {runScript(s(s1)).$ shouldBe Success(__`5`)}
    test(2) {runScript(s(s2)).$ shouldBe Success(__`"Exception happened!"`)}
  }

  koan(3)(
    """
    | The dataflow may have any number of result-matching clauses,
    | with all syntactic possibititles that Scala partial functions offer.
    """
  ) {
    script..
      s(lhs: Script[Any]) =
        lhs ~~(x: Int   )~~> ^(x + 2)
           +~~(x: String)~~> ^("Result: " + x)
           +~~(x: Double)~~> ^(x * 2)

      s1 = ^1
      s2 = ^"foo"
      s3 = ^2.2

    test(1) {runScript(s(s1)).$ shouldBe Success(__`3`)}
    test(2) {runScript(s(s2)).$ shouldBe Success(__`"Result: foo"`)}
    test(3) {runScript(s(s3)).$ shouldBe Success(__`4.4`)}
  }

  koan(4)(
    """
    | Dataflow map is like dataflow; the difference being that
    | the right hand side is a Scala value term, that becomes the
    | result value of the parent script.
    """
  ) {
    script s = {!1!} ~~(x: Int)~~^(x*2)

    test(1) {runScript(s).$ shouldBe Success(__`2`)}
  }

  koan(5)(
    """
    | Dataflow map may also have multiple matching clauses
    """
  ) {
    script s(lhs: Script[Any]) = lhs ~~(x: Int   )~~^(x*2)
                                        +~~(x: Double)~~^(x*3)

    test(1) {runScript(s([^2  ])).$ shouldBe Success(__`4`)}
    test(2) {runScript(s([^2.0])).$ shouldBe Success(__`6.0`)}
  }

  koan(6)(
    """
    | Dataflow map has a shorthand version. Its right-hand side must
    | be a function from whatever left-hand side returns to whatever
    | you want the script's result to be.
    """
  ) {
    def int2string(x: Int): String = x.toString

    script s = ^1 ~~^ int2string

    test(1) {runScript(s).$ shouldBe Success(__`"1"`)}
  }

}
