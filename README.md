# SubScript Koans
SubScript Koans are inspired by Scala Koans, Ruby Koans and similar projects.
They are a compilation of small exercises designed to teach you SubScript.

To get started with the project:

1. [Download](#) and unzip the latest release. *Note: so far, there were no releases. In order to test the project out, clone it (`git clone https://github.com/scala-subscript/koans.git`) and turn the solutions off (`./solutions off` from the root of the project).*
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

If you want to skip some koans, you can comment them out in `src/test/PathToEnlightenment.scala`.

**IMPORTANT:** Currently SubScript files are translated by a combination of a preprocessor and the normal Scala compiler. This implies that error messages that the Scala compiler generatess may point to wrong lines. Please be guided by koan and test ids (Koan 1, Test 1) to identify failing tests, not by the line number indicated in the error!

## For developer

To get started contributing to the project, first clone the repository (fork&clone if you plan to do pull requests): `git clone https://github.com/scala-subscript/koans.git`.

The koans are located at `/koan-templates` directory, at the root of the project. The sources in that directory are templates that contain solutions next to the placeholders. The format is as follows: ``__`solution```.

In order to test your project with solutions in place, first run `./solutions on` from the root of the project. This will copy the koans with solutions to the test directory - you can enter the sbt console and work with them as described above.

In order to test the project without solutions, run `./solutions off`.

In order to release a new version of the koans, execute `./release version`, where `version` is your version. A zip will be generated under `distribution` folder ready to be published.