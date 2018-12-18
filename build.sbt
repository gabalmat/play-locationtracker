name := """locationtracker"""
organization := "org.csc750"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.23.1"
