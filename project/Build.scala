import sbt._
import Keys._
import scala._

import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseCreateSrc
import spray.revolver.RevolverPlugin.Revolver
import com.github.retronym.SbtOneJar

object loggerBuild extends Build {
  lazy val logger = Project ("logger", base = file ("."), settings = projectSettings)
  def projectSettings = Project.defaultSettings ++
    Seq (
      organization := "com.busymachines",
      version := "1.0.0-SNAPSHOT"
    )++
    projectDependencies ++
    Revolver.settings ++
    SbtOneJar.oneJarSettings++
    compilerSettings

  def projectDependencies= Seq(libraryDependencies++=jars)++repositories

  def jars = Seq (
    "com.busymachines" %% "busymachines-commons" % "0.4-SNAPSHOT" withSources() changing(),
    "org.scalatest" %% "scalatest" % "2.2.0" withSources(),
    "org.pegdown" % "pegdown" % "1.4.1" withSources(),
    "junit" % "junit" % "4.11" % "test" withSources(),
    "joda-time" % "joda-time" % "2.1" withSources(),
    "junit" % "junit" % "4.11" % "test" withSources(),
    "org.elasticsearch" % "elasticsearch" % "1.3.1" withSources(),
    "com.codahale.metrics" % "metrics-core" % "3.0.1" withSources(),
    "org.apache.logging.log4j" % "log4j-api" % "2.0.1" withSources(),
    "org.apache.logging.log4j" % "log4j-core" % "2.0.1" withSources(),
    "io.spray" %% "spray-json" % "1.2.6" withSources()

  )

  def repositories = Seq (
    resolvers += Resolver.url ("busymachines snapshots", url ("http://archiva.busymachines.com/repository/snapshots/"))(Resolver.ivyStylePatterns),
    resolvers += "spray repo" at "http://repo.spray.io",
    resolvers += "spray nightlies repo" at "http://nightlies.spray.io",
    resolvers += "Typesafe Maven Releases" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Typesafe Maven Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    resolvers +=  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
    resolvers +=  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    resolvers += "spray repo" at "http://repo.spray.io",
    resolvers += "Apache log4j repo" at "http://archive.apache.org/dist/logging/log4j/",
    credentials += Credentials (Path.userHome / ".ivy2" / ".credentials_busymachines_snapshots"),
    credentials += Credentials (Path.userHome / ".ivy2" / ".credentials_busymachines_releases")
  )

  def compilerSettings = Seq (
    scalaVersion := "2.11.1",
    javacOptions in Compile ++= Seq ("-encoding", "utf8", "-g"),
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-encoding", "utf8"),
    publishMavenStyle := false,
    exportJars := true,
    parallelExecution in Global := false,
    javaOptions in Test += "-Xmx1500m",
    javaOptions += "-Xmx1200m",
    fork in test := true)
}