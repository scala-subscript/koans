import complete.DefaultParsers._

scalaVersion := "2.11.7"
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "org.subscript-lang" %% "subscript-swing" % "3.0.2-SNAPSHOT"
SubscriptSbt.projectSettings

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

testOptions in Test += Tests.Argument("-oC")


lazy val solutionsOn        = taskKey [Unit]("Turn the solutions on")
lazy val setEnvironmentTask = inputKey[Unit]("Sets environment variable")

lazy val koans              = inputKey[Unit]("Runs koans")
lazy val show               = inputKey[Unit]("Runs koans with hints")

solutionsOn := {"./solutions off"!}

setEnvironmentTask <<= Def.inputTask {
  val args = spaceDelimited("<arg>").parsed
  for {
    arg   <- args
    key   = arg.takeWhile(_ != ':')
    value = arg.reverse.takeWhile(_ != ':').reverse
  } System.setProperty(key, value)
} dependsOn solutionsOn

koans := setEnvironmentTask.parsed.flatMap {_ =>
  (testOnly in Test).fullInput(" PathToEnlightenment").parsed
}.value

show := setEnvironmentTask.parsed.flatMap {_ =>
  System.setProperty("show", "1")
  (testOnly in Test).fullInput(" PathToEnlightenment").parsed
}.value