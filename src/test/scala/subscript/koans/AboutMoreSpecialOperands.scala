package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util._

class AboutMoreSpecialOperands extends KoanSuite with OperatorKoansHelper {

  koan("""Loop, `...`, is an operand that loops its parent sequence.""") {
    script s = b c ...
    
    test(1) {
      runWithInput(s)(b, c, b, c, b)
      activatedShouldBe(___)
    }
  }

  koan("""It doesn't matter where you put `...` in a sequence for it to have
        | effect.""") {
    script..
      s1 = b ... c
      s2 = ... b c

    test(1) {  
      runWithInput(s1)(b, c)
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s2)(b, c, b)
      activatedShouldBe(___)
    }
  }

  koan("""`while(p)` is an operand that works like `...`, but loops its
        | parent sequence until predicate `p` is true.""") {
    var i = 0
    script s = while(i < 2) b c {!i += 1!}

    test(1) {
      runWithInput(s)(b, c)
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s)(b, c, b, c)
      activatedShouldBe(___)
    }
  }

  koan("""As `while` is an operand, it also doesn't matter where you put it
        | in sequence.""") {
    var i = 0
    script s = b while(i < 2) c {!i += 1!}

    test(1) {
      runWithInput(s)(b, c)
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s)(b, c, b, c)
      activatedShouldBe(___)
    }
  }

  koan("""`break` breaks the activation of its parent operand.""") {
    script s = b && c && break && d

    test(1) {
      runWithInput(s)()
      activatedShouldBe(___)
    }
  }

  koan("""Optional break `break?` works like `break`, but once an activation
        | happens in one of its previously activated operands
        | it resumes its activation sequence.""") {
    script s = b && c && break? && d

    test(1) {
      runWithInput(s)()
      activatedShouldBe(___)
    }

    test(2) {
      runWithInput(s)(c)
      activatedShouldBe(___)
    }

    test(3) {
      runWithInput(s)(b)
      activatedShouldBe(___)
    }
  }

}