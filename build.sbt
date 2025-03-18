ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.4"

lazy val dev = Project("abwcf-dev", file("."))
  .settings(
    name := "ABWCF Development"
  )
  .dependsOn(abwcf)

lazy val abwcf = project in file("Actor-Based Web Crawling Framework")

val logbackVersion = "1.5.18" //License: EPL / LGPL (dual license) → Derivative work must also be licensed under one of these.

libraryDependencies ++= Seq(
  //These dependencies should not be included in ABWCF release packages:
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime
)
