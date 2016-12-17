lazy val root = (project in file(".") settings(
  organization := "com.soundcloud.kyyro",
  name := "ninety-nine",
  version := "0.1.0",
  scalaVersion := "2.12.1",

  libraryDependencies ++= Seq(
    "org.scalactic"  %% "scalactic"  % "3.0.1",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
    "org.scalatest"  %% "scalatest"  % "3.0.1"  % "test"
  )
))