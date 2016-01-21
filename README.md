# SubScript Koans
SubScript Koans are inspired by Scala Koans, Ruby Koans and similar projects.
They are a compilation of small exercises designed to teach you SubScript.

To get started with the project:

1. Clone it with `git clone https://github.com/scala-subscript/koans.git`
2. Go to the root directory: `cd koans`
3. Open the SBT console: `sbt`
4. Run the koans from SBT console: `test-only PathToEnlightenment`

You'll see an error indicating the koan you need to work on. For example:
```
[info] AboutSubScript:
[info] - Scripts are defined with a `script` keyword.
[info] Scala code blocks can be used in the script body in the `{! ... !}` braces
[info] Scripts can be executed using the execute() method. *** FAILED ***
[info]   true was not equal to "Fill me in" (AboutSubScript.scala:22)
```

This means you need to work on AboutSubScript koan. Find it at `src/test/scala/subscript/koans/` folder, open it and make the tests pass.
The instructions in the koans will be helpful for you.

**IMPORTANT:** so far, SubScript works by rewriting the sources. This means scripts may take more lines in the rewritten (pure Scala) state than they take in SubScript state. Because of this, compiler can't report the line on which an error occured reliably. More often then not, failed tests will point to a wrong line.