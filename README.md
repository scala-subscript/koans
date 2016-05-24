# SubScript Koans
SubScript Koans are small exercises that teach you SubScript. They have been inspired by Scala Koans and Ruby Koans.

## Getting Started

1. [Download](https://github.com/scala-subscript/koans/releases/download/v1.0.1/subscript-koans-1.0.1.zip) and unzip the latest release to a directory of your choice.
2. Navigate to that directory from your command line.
3. Open the SBT console: `sbt`.
4. Run the koans from the SBT console: `koans`.

You will see all your completed koans colored in green, and the koan suite to be worked on colored in red:
```
[info] AboutSubScript:
[info] - Koan 1 *** FAILED ***
[info]   Test 1 is wrong: think more about it! (KoanSuite.scala:28)
```

This means you need to work on AboutSubScript koan suite, Koan 1, Test 1. 
The source is at `src/test/scala/subscript/koans/` folder.
Your task is to edit the file so that the tests passes.
The instructions in the koans will be helpful for you.

In case you can't solve a particular test, you may change a call to the `test` method into a call to `show`;
then the program will give you a clue on the next run. 
Alternatively you may run the koans from the SBT console using the command `show` instead of `koans`.

**IMPORTANT:** Currently SubScript files are translated by a combination of a preprocessor and the normal Scala compiler. This implies that error messages that the Scala compiler generatess may point to wrong lines. Please be guided by koan and test ids (Koan 1, Test 1) to identify failing tests, not by the line number indicated in the error!

## Controlling test workflow

You can specify certain options after `koans` to control the test flow.

E.g.,
```
koans about:EpsilonAndDelta koan:1 test:2,3
```
Alternatively you can specify which koans to skip:
```
koans skip:Dataflow,EpsilonAndDelta
```
Or you can edit the following file to comment out parts of the list of koans that will be executed:
```
koans/src/test/scala/PathToEnlightenment.scala
```

Use `show` instead of `koans` to receive clues about errors:
```
show about:Dataflow, AdvancedSyntax
```
You can debug a koan test in a graphical debugger as follows:
```
debugKoans about:Dataflow koan:1,2
```

You can debug a koan test in a textual debugger as follows:
```
trace about:Dataflow koan:1 test:1
```

## For developers

To get started contributing to the project, first clone the repository (fork&clone if you plan to do pull requests):
```
git clone https://github.com/scala-subscript/koans.git
```

The koans are located at directory `/koan-templates`, at the root of the project.
The sources in that directory are templates that contain solutions next to the placeholders.
The format is as follows: 

    __`solution`
    
E.g.,

    __`1`
    
You would need to test all the koans in a solved state to reveal all the possible runtime errors. 
When you run `test-only PathToEnlightenment`, the placeholders are are automatically substituted by the solutions.

In order to release a new version of the koans, execute `./release version`, where `version` is your version.
A zip file will be generated under `distribution` folder ready to be published.
