scalaVersion := "2.11.7"
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "org.subscript-lang" %% "subscript-swing" % "3.0.0-SNAPSHOT"
SubscriptSbt.projectSettings

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

testOptions in Test += Tests.Argument("-oC")
