scalaVersion := "2.11.7"
libraryDependencies += "org.subscript-lang" %% "subscript-swing" % "2.0.0"
SubscriptSbt.projectSettings

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

testOptions in Test += Tests.Argument("-oC")