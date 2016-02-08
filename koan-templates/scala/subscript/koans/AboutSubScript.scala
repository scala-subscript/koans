package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutSubScript extends KoanSuite {
  koan(1)(
    """
    | Imports, scripts and atomic actions:
    |
    | To use SubScript in a file, it should have these two import statements:
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
    | Sequential composition:
    |
    | In the previous koan, the script expression consisted only of a single operand: `{!flag = true!}`.
    |
    | We can make a sequence of 2 such `atomic actions` using a sequential operator.
    | To make a sequential composition you may concatenate two terms on the same line.
    | This deviates from Scala; there such concatenation means function application (`f x` is the same as `f(x)`)
    """
  ) {
    var    i    = 0
    script foo = {! i += 1 !} {! i += 1 !}

    test(1) { runScript(foo); i shouldBe __ }
  }

  koan(3)(
    """
    | Script calls and method calls:
    |
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
