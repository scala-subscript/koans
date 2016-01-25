package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutAdvancedSyntax extends KoanSuite {
  koan("""You can use `let` keyword to exeute one-linear Scala code snippets.""") {
    var a = 0
    script s = let a = 1

    test(1) {
      runScript(s)
      a shouldBe __
    }
  }

  koan("""You can define vars and vals in scripts.""") {
    var a = 0
    script s = val b = 2
               let a = b

    test(1) {           
      runScript(s)
      a shouldBe __
    }
  }

  koan("""You can use inline definition of scripts from Scala code.""") {
    var a = 0
    val s = ([let a = 3])

    test(1) {
      runScript(s)
      a shouldBe __
    }
  }
}