ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.4"

lazy val dev = Project("abwcf-dev", file("."))
  .settings(
    name := "ABWCF Development"
  )
  .dependsOn(abwcf)

lazy val abwcf = project in file("Actor-Based Web Crawling Framework")

val postgresVersion = "42.7.5" //License: BSD-2-Clause
val logbackVersion = "1.5.18" //License: EPL / LGPL (dual license)

libraryDependencies ++= Seq(
  //These dependencies should not be included in ABWCF release packages:
  "org.postgresql" % "postgresql" % postgresVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime
)
