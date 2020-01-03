ThisBuild / organization := "com.github"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.1"

lazy val helloworld = (project in file("./services/helloworld"))

lazy val root = (project in file("."))
  .aggregate(helloworld)

