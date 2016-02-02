package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutSubScript extends KoanSuite {
  koan(1)(
    """
    |To use SubScript in a file, it should have these two import statements:
    |
    | `import subscript.language`
    | `import subscript.Predef._`
    | See the top of this source file.
    | 
    | SubScript extends Scala with refinements that are called `scripts`.
    | These are much like methods. Their definition starts with the
    | keyword `script` rather than the keyword `def`.
    |
    | The Script body is a so called `script expression`.
    | These are composed from several kinds of operators and terms.
    | One such a kind of term is a so called `atomic action`.
    | This is specified as a piece of regular Scala code,
    | enclosed in a `{! ... !}` brace pair.
    |
    | Scripts can be executed using the runScript() method.
    |
    | To solve the koans, fill in the blanks `__`.
    | For example, in this first koan you need to replace `__` with `true`
    """
  ) {
    var    flag = false
    script foo  = {! flag = true !}

    test(1) { runScript(foo); flag shouldBe __ }
  }

  koan(2)(
    """
    | In the previous koan, the script expression consisted only of
    | one operand: `{!flag = true!}`.
    |
    | We can make a sequence of 2 such `atomic actions` using a sequential operator.
    | There are three such sequential operators:
    | - nothing (most often one or more spaces between terms on a single line).
    |   This is unlike the white space operator in Scala: there it denotes function application
    | - a semicolon (;). This is much like the meaning in Scala
    | - a new line, for which about the same semicolon inference rules hold as in Scala
    |
    | Note that, like in Scala, the white space operator has a high priority,
    | whereas the semicolon and the new line have a low priority.
    | The new line has even a lower priority than the semicolon.
    | This has some significance which will appear when Iterations are discussed.
    """
  ) {
    var    i    = 0
    script foo1 = {!i += 1!}{!  i += 1 !}
    script foo2 = {!i += 1!} {! i += 1 !}
    script foo3 = {!i += 1!};{! i += 1 !}
    script foo4 = {!i += 1!}
                  {!i += 1!}

    test(1) { runScript(foo1); i shouldBe __ }
    test(2) { runScript(foo2); i shouldBe __ }
    test(3) { runScript(foo3); i shouldBe __ }
    test(4) { runScript(foo4); i shouldBe __ }
  }

  koan(3)(
    """
    | Other operands in script expressions are for instance
    | script calls and method calls. Often these look the same:
    """
  ) {
    var    i   = 0
    script a   = {! i += 1 !}
    script b   = {! i += 2 !}
    def    c   =    i += 3

    script foo1 =             a b c
    script foo2 = {! i = 0 !} a b c {! i += 1 !}

    test(1) { runScript(foo1); i shouldBe __ }
    test(2) { runScript(foo2); i shouldBe __ }
  }
}