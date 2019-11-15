import sbt._

object Dependencies {
  val catsVersion       = "2.0.0"
  val refinedVersion    = "0.9.10"
  val monocleVersion    = "2.0.0"
  val circeVersion      = "0.11.1"
  val scoptVersion      = "4.0.0-RC2"
  lazy val scalaTest    = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val catsEffect   = "org.typelevel" %% "cats-effect" % catsVersion
  lazy val cats         = "org.typelevel" %% "cats-core" % catsVersion
  lazy val refined      = "eu.timepit" %% "refined" % refinedVersion
  lazy val refinedCats  = "eu.timepit" %% "refined-cats" % refinedVersion
  lazy val monocleCore  = "com.github.julien-truffaut" %% "monocle-core" % monocleVersion
  lazy val monocleMacro = "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion
  lazy val circeCore    = "io.circe" %% "circe-core" % circeVersion
  lazy val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  lazy val circeParser  = "io.circe" %% "circe-parser" % circeVersion
  lazy val scopt        = "com.github.scopt" %% "scopt" % scoptVersion
}
