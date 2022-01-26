name := """play-scala-myapp"""
organization := "com.suu.play"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += jdbc
libraryDependencies += guice
libraryDependencies += ehcache
libraryDependencies += evolutions
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.5"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.12"

// libraryDependencies ++= Seq(
//   jdbc,
//   anorm, 
//   cache,
//   "mysql" % "mysql-connector-java" % "8.0.12"
// ) 

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.suu.play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.suu.play.binders._"
