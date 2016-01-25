package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutResultValues extends KoanSuite {

  koan("""Scripts may have result values (similar to values returned from
        | methods. `runScript(s)` called from Scala code returns a
        | ScriptExecutor that executed `s`, its `$` property contains the
        | result value of `s` in a `Try[Any]`. Normally, all the scripts have
        | their result value set to `null`.""") {
    script s = [+]
    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""You can specify that the script's result value should be that
        | of some of its operands. You do so by suffixing that operand
        | with `^`.""") {
    script s = {!1!}^ {!2!}
    test(1) {
      runScript(s).$ shouldBe Success(__)
    }
  }

  koan("""If there's only one operand capable of having a result value
        | is present in a script, its result becomes script's result.""") {
    script s = {!1!}
    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""Each subsequent caret `^` overwrites script's result value.""") {
    script s = {!1!}^ {!2!}^
    test(1) {
      runScript(s).$ shouldBe __
    }
  }

  koan("""For convenience, if you need a script to have a result of a
        | literal, you can directly prefix that literal with `^`""") {
    script s = {!!} ^'r'
    test(1) {
      runScript(s).$ shouldBe __
    }
  }

}