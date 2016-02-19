# SubScript Koans
SubScript Koans are inspired by Scala Koans, Ruby Koans and similar projects.
They are a compilation of small exercises designed to teach you SubScript.

To get started with the project:

1. ~~[Download](#) and unzip the latest release~~ **NO RELEASES SO FAR! Follow next step (1) to generate a release manually. STAY TUNED: We'll release soon!**
1. Clone the repository: `git clone https://github.com/scala-subscript/koans.git`, go to its root and run `./release 1.0.0`. You'll get a release zip file under your `distribution` directory. Unzip it somewhere and `cd` to that folder.
2. Go to the root directory: `cd koans`
3. Open the SBT console: `sbt`
4. Run the koans from SBT console: `koans`

You'll see all your completed koans colored in green and the koan suite to be worked on colored in red:
```
[info] AboutSubScript:
[info] - Koan 1 *** FAILED ***
[info]   Test 1 is wrong: think more about it! (KoanSuite.scala:28)
```

This means you need to work on AboutSubScript koan suite, Koan 1, Test 1. Find it at `src/test/scala/subscript/koans/` folder, open it and make the tests pass.
The instructions in the koans will be helpful for you.

In case you can't solve a particular test, rename `test` to `show` and the program will give you a clue on the next run.

**IMPORTANT:** Currently SubScript files are translated by a combination of a preprocessor and the normal Scala compiler. This implies that error messages that the Scala compiler generatess may point to wrong lines. Please be guided by koan and test ids (Koan 1, Test 1) to identify failing tests, not by the line number indicated in the error!

## Controlling test workflow

You can specify certain options after `koans` to control the test flow.

You can specify which precisely a koan suite, koan and test to run as follows (all the parameters are optional):
```
koans about:Dataflow,EpsilonAndDelta koan:1,2 test:2,3
```

You can skip the koan suites as follows:
```
koans skip:Dataflow,EpsilonAndDelta
```

You can run all the koans with clues using `show` instead of `koans`.

You can debug the koans in a graphical debugger as follows:
```
debugKoans about:Dataflow koan:1 test:1
```

You can debug the koans in a textual debugger as follows:
```
trace about:Dataflow koan:1 test:1
```

## For developer

To get started contributing to the project, first clone the repository (fork&clone if you plan to do pull requests): `git clone https://github.com/scala-subscript/koans.git`.

The koans are located at `/koan-templates` directory, at the root of the project. The sources in that directory are templates that contain solutions next to the placeholders. The format is as follows: ``__`solution```.

You need to test all the koans in a solved state to reveal all the possible runtime errors. When you run `test-only PathToEnlightenment`, the solutions are automatically substituted in place of the placeholders.

In order to release a new version of the koans, execute `./release version`, where `version` is your version. A zip will be generated under `distribution` folder ready to be published.