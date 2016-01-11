package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutSubScript extends KoanSuite {
  koan("""Scripts are defined with a `script` keyword.
         |Scala code blocks can be used in the script body
         |in the `{! ... !}` braces.
         |Scripts can be executed using the execute() method.
         |
         |Fill in the blanks `__` in order to solve the koan.
         |For example, in this exercise you need to replace `__` with `true`""") {
    
    var flag = false
    script foo = {!flag = true!}
    runScript(foo)

    flag shouldBe __
  }

  koan("""Scala code blocks can be executed sequentialy using
         |space as a sequential operator""") {
    var i = 1
    script foo = {!i += 1!} {!i += 1!}
    runScript(foo)

    i shouldBe __
  }
}