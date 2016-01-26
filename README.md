# SubScript Koans
SubScript Koans are inspired by Scala Koans, Ruby Koans and similar projects.
They are a compilation of small exercises designed to teach you SubScript.

To get started with the project:

1. Clone it with `git clone https://github.com/scala-subscript/koans.git`
2. Go to the root directory: `cd koans`
3. Open the SBT console: `sbt`
4. Run the koans from SBT console: `test-only PathToEnlightenment`

You'll see all your completed koans colored in green and the koan suite to be worked on colored in red:
```
[info] AboutSubScript:
[info] - Koan 1 *** FAILED ***
[info]   Test 1 is wrong: think more about it! (KoanSuite.scala:28)
```

This means you need to work on AboutSubScript koan suite, Koan 1, Test 1. Find it at `src/test/scala/subscript/koans/` folder, open it and make the tests pass.
The instructions in the koans will be helpful for you.

In case you can't solve a particular test, rename `test` to `giveUp` and the program will give you a clue on the next run.

**IMPORTANT:** so far, SubScript works by rewriting the sources. This means error messages may point to wrong lines. Please be guided by koan and test ids (Koan 1, Test 1) to identify failing tests, not by the line number indicated in the error!