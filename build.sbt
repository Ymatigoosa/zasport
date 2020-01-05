ThisBuild / organization := "com.github"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := Settings.scalaVersion
ThisBuild / scalacOptions := Settings.defaultScalacOptions

lazy val helloworld = (project in file("./services/helloworld"))

lazy val `webgateway-app` = (project in file("./services/webgateway/app"))
  .settings(
    libraryDependencies ++= Seq(
      Deps.caliban,
      Deps.calibanAkkaHttp,
      Deps.calibanCats,
      Deps.akkaHttp,
      Deps.akkaStream,
      Deps.cats,
      Deps.tethys
    )
  )

lazy val root = (project in file("."))
  .aggregate(
    helloworld,
    `webgateway-app`
  )
