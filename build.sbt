scalaVersion := "2.11.7"
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "org.subscript-lang" %% "subscript-swing" % "3.0.2-SNAPSHOT"
SubscriptSbt.projectSettings

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

testOptions in Test += Tests.Argument("-oC")

lazy val solutionsOn = taskKey[Unit]("Turn the solutions on")

solutionsOn := {"./solutions on"!}

testOnly <<= (testOnly in Test) dependsOn solutionsOn

fork in Test := true