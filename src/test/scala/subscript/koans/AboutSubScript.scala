package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutSubScript extends KoanSuite {
  koan("""Scripts are defined with a `script` keyword.
       |  Scala code blocks can be used in the script body in the `{! ... !}` braces
       |  Scripts can be executed using the execute() method.""") {
    
    var flag = false
    script foo = {!flag = true!}
    runScript(foo)

    flag shouldBe true
  }
}