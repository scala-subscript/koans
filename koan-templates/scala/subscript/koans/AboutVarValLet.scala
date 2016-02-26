package subscript.koans

import subscript.language
import subscript.Predef._

import subscript.koans.util.KoanSuite

class AboutVarValLet extends KoanSuite {

  koan(1)(
    """
    | You can define vars and vals in scripts.
    | These should be placed in a sequence, and not at the end.
    | The initializer expression (at the right hand side of `=`) may contain operators such as `+` at the top level.
    | Normally the initializer expression ends at the end of the line, or at a semicolon.
    """
  ) {
      var g = 0

      script s = var i = 0
                 val j = 1+2
                 {!  g = j !}

      test(1) {runScript(s); g shouldBe __`3`}
  }

  koan(2)(
    """
    | With the `let` keyword you can specify tiny code fragments without the brace pair.
    | This is a recommended style, for it looks more quiet.
    |
    | If after `let` comes an assignemnt, then the same rules apply for the scope of the right hand side
    | as for the `var` and `val` initializers
    """
  ) {
      var g = 0

      script s1 = var i = 0
                  val j = 1+2
                  let g = j+3

      script s2 = var i = 0; val j = 1+2; let g = j+3

      test(1) {runScript(s1); g shouldBe __`6`}
      test(2) {runScript(s2); g shouldBe __`6`}
  }
}


