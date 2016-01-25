package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutConditionalOperators extends KoanSuite with OperatorKoansHelper {

  koan("""`if` operator executes then part if its predicate (simple Scala
        | expression) is true, otherwise - else part""") {
    script s(predicate: Boolean) = if predicate then b else c

    test(1) {
      runWithInput(s(true))()
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s(false))()
      activatedShouldBe(___)
    }
  }

  koan("""`do` operators works like `if`, but its predicate is a SubScript
        | expression rather then Scala expression. If the predicate succeeds,
        | `then` part is executed; if it ends without success - `else` part.""") {
    script..
      s1 = do b then c else d
      s2 = do b [-] then c else d

    test(1) {  
      runWithInput(s1)(b)
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s2)(b)
      activatedShouldBe(___)
    }
  }

}