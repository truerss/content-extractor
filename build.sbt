
ThisBuild / version := "1.0.5"
ThisBuild / organization := "io.github.truerss"
ThisBuild / licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
sonatypeCredentialHost := "s01.oss.sonatype.org"
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots/")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

ThisBuild / homepage := Some(url("https://github.com/truerss/truerss"))
ThisBuild / scmInfo := Some(ScmInfo(url("https://github.com/truerss/content-extractor"), "git@github.com:truerss/content-extractor.git"))
ThisBuild / developers := List(Developer("mike", "mike", "mike.fch1@gmail.com", url("https://github.com/fntz")))
ThisBuild / licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

publishMavenStyle := true
crossPaths := false

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.15.3",
  "org.junit.jupiter" % "junit-jupiter" % "5.7.1" % Test
)