# SubScript Koans
SubScript Koans are inspired by Scala Koans, Ruby Koans and similar projects.
They are a compilation of small exercises designed to teach you SubScript.

To get started with the project:

1. ~~[Download](#) and unzip the latest release~~ **NO RELEASES SO FAR! Follow next step (1) to generate a release manually. STAY TUNED: We'll release soon!**
1. Clone the repository: `git clone https://github.com/scala-subscript/koans.git`, go to its root and run `./release 1.0.0`. You'll get a release zip file under your `distribution` directory. Unzip it somewhere and `cd` to that folder.
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

In case you can't solve a particular test, rename `test` to `show` and the program will give you a clue on the next run.

If you want to skip some koans, you can comment them out in `src/test/PathToEnlightenment.scala`.

**IMPORTANT:** Currently SubScript files are translated by a combination of a preprocessor and the normal Scala compiler. This implies that error messages that the Scala compiler generatess may point to wrong lines. Please be guided by koan and test ids (Koan 1, Test 1) to identify failing tests, not by the line number indicated in the error!

## Controling test workflow

You can specify how much tests should be executed before the test suite returns. For example, in order to set this number to 10, invoking the following command from SBT console:
```
set javaOptions in Test += "-Dmax=10"
```

You can also make all the wrong tests output a solution:
```
set javaOptions in Test += "-Dshow=1"
```

You can cancel this behaviour by writing `0` instead of `1`.

## For developer

To get started contributing to the project, first clone the repository (fork&clone if you plan to do pull requests): `git clone https://github.com/scala-subscript/koans.git`.

The koans are located at `/koan-templates` directory, at the root of the project. The sources in that directory are templates that contain solutions next to the placeholders. The format is as follows: ``__`solution```.

You need to test all the koans in a solved state to reveal all the possible runtime errors. When you run `test-only PathToEnlightenment`, the solutions are automatically substituted in place of the placeholders.

In order to release a new version of the koans, execute `./release version`, where `version` is your version. A zip will be generated under `distribution` folder ready to be published.