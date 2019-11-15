import Dependencies._

ThisBuild / scalaVersion      := "2.12.6"
ThisBuild / version           := "0.1.0-SNAPSHOT"
ThisBuild / organization      := "fr.flaminem"
ThisBuild / organizationName  := "flaminem"
ThisBuild / scalafmtOnCompile := true

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Flaminem-ball-clock",
    libraryDependencies ++= Seq(
      cats,
      catsEffect,
      refined,
      monocleCore,
      monocleMacro,
      circeCore,
      circeGeneric,
      circeParser,
      scopt,
      scalaTest % Test
    ),
    scalacOptions ++= Seq("-feature",
                          "-deprecation",
                          "-unchecked",
                          "-language:postfixOps",
                          "-language:higherKinds",
                          "-Ypartial-unification")
  )
