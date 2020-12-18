// The simplest possible sbt build file is just one line:

scalaVersion := "2.12.6"

name := "plant-waterer"
organization := "even-financial"
version := "1.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.github.alonsodomin.cron4s" %% "cron4s-core" % "0.6.1",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3"
)
