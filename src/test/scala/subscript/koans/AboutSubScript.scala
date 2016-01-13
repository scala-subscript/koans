package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutSubScript extends KoanSuite {
  koan("""Before using SubScript, you should define a top-level import of
        | `subscript.language` (see top of this source file). You should also
        | `import subscript.Predef._`.
        | 
        | Scripts are defined with a `script` keyword.
        | Scala code blocks can be used in the script body
        | in the `{! ... !}` braces.
        | Scripts can be executed using the runScript() method.
        |
        | Fill in the blanks `__` in order to solve the koan.
        | For example, in this exercise you need to replace `__` with `true`""") {
    
    var flag = false
    script foo = {!flag = true!}
    runScript(foo)

    flag shouldBe __
  }

  koan("""Script bodies are always SubScript expressions - a sequence of
        | operands bound by SubScript operators. Scala code blocks are
        | operands. In previous koan, the expression consisted only of
        | one operand.
        |
        | The most basic operator is a sequence - its operands are
        | executed sequentialy. It is denoted as a space.""") {
    var i = 1
    script foo = {!i += 1!} {!i += 1!}
    runScript(foo)

    i shouldBe __
  }

  koan("""Scripts can call one another or Scala methods. Script calls and
        | method calls are also operands.""") {
    var i = 1
    script foo = a b
    script a = {!i += 1!}
    script b = {!i += 2!}

    runScript(foo)

    i shouldBe __
  }
}