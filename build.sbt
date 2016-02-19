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
lazy val debugKoans         = inputKey[Unit]("Runs koans with debugger")
lazy val trace              = inputKey[Unit]("Runs koans with tracer")


solutionsOn := {"./solutions on"!}

setEnvironmentTask <<= Def.inputTask {
  val args = spaceDelimited("<arg>").parsed

  System.clearProperty("about")
  System.clearProperty("koan" )
  System.clearProperty("test" )
  System.clearProperty("skip" )

  System.clearProperty("debug")
  System.clearProperty("trace")

  for {
    arg   <- args
    if arg.contains(':')
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

debugKoans := setEnvironmentTask.parsed.flatMap {_ =>
  System.setProperty("debug", "1")
  (testOnly in Test).fullInput(" PathToEnlightenment").parsed
}.value

trace := setEnvironmentTask.parsed.flatMap {_ =>
  System.setProperty("trace", "1")
  (testOnly in Test).fullInput(" PathToEnlightenment").parsed
}.value