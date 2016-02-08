package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

import scala.util.{Try, Success, Failure}

class AboutResultValues extends KoanSuite {

  koan(1)(
    """
    | Scripts may have result values.
    | These similar to values returned from methods.
    | The naming is different because a script does not really return.
    | Rather it may succeed zero or more times, and finally it deactivates.
    |
    | But a script may also terminate in failure. That will often be
    | due to an exception. Normally the exception value is also available
    | in the result value.
    |
    | For this purpose, the called script has its result value encapsulated in a
    | property named '$', of type Try[T], with T the result type of the script.
    | A Try[T] is either a Success[T] that encapsulates the real result value,
    | or a Failure, which may encapsulate an exception (if absent: null).
    |
    | The call to `runScript(s)` returns a structure called ScriptExecutor.
    | This has executed `s`. The `$` property of the call to s becomes available in the
    | `$` property of the executor.
    |
    | The default value for the result values and the exceptions is null.
    """
  ) {
    script s1 = [-]
    script s2 = [+]

    test(1) {runScript(s1).$ shouldBe Failure(null)}
    test(2) {runScript(s2).$ shouldBe Success(__)}
  }

  koan(2)(
    """
    | A code fragment such as {!1!} also has a result value;
    | it is equal to the value that the enclosed Scala code block
    | yields when it is executed (here 1).
    |
    | You can specify that the script's result value should be set
    | to the one of one or more specific operands.
    | You do so by adding a postfix symbol `^` (known as 'caret') to those operands.
    |
    | Each subsequent success that reaches such a caret `^` overwrites
    | the script's result value.
    |
    | If there is only a single operands that could syntactically have
    | a caret, and if it has none, then it is supposed to have one as yet.
    """
  ) {
    script s1 = {!1!}
    script s2 = {!1!}^
    script s3 = {!1!}^ {!2!}
    script s4 = {!1!}  {!2!}^
    script s5 = {!1!}  {!2!}
    script s6 = {!1!}^ {!2!}^

    test(1) {runScript(s1).$ shouldBe Success(__)}
    test(2) {runScript(s2).$ shouldBe Success(__)}
    test(3) {runScript(s3).$ shouldBe Success(__)}
    test(4) {runScript(s4).$ shouldBe Success(__)}
    test(5) {runScript(s5).$ shouldBe Success(__)}
    test(6) {runScript(s6).$ shouldBe Success(__)}
  }

  koan(3)(
    """
    | For convenience, if you need a script to have a result of a
    | literal, you can directly prefix that literal with `^`
    """
  ) {
    script s = {!!} ^'r'

    test(1) {runScript(s).$ shouldBe Success(__)}
  }

  koan(4)(
    """
    | TBD: calls, other code fragments, [expr]^
    """
  ) {
  }

}