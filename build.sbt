//TODO: Replace sbt with Maven if possible.
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.4"
ThisBuild / assemblyMergeStrategy := {
  case file if file.endsWith("module-info.class") => MergeStrategy.concat
  case other =>
    val strategy = (ThisBuild / assemblyMergeStrategy).value
    strategy(other)
}

lazy val dev = Project("abwcf-dev", file("."))
  .settings(
    name := "ABWCF Development",
    assembly / assemblyJarName := "abwcf-dev.jar"
  )
  .dependsOn(abwcf)

lazy val abwcf = (project in file("ABWCF"))
  .settings(
    assembly / assemblyJarName := "abwcf.jar"
  )

val postgresVersion = "42.7.5" //License: BSD-2-Clause
val logbackVersion = "1.5.18" //License: EPL / LGPL (dual license)
val openTelemetryApiVersion = "1.49.0" //License: Apache-2.0

libraryDependencies ++= Seq(
  //These dependencies should not be included in ABWCF release packages:
  "org.postgresql" % "postgresql" % postgresVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime,
  "io.opentelemetry" % "opentelemetry-api" % openTelemetryApiVersion
)
