import sbt._

object Deps {
  val calibanVersion  = "0.4.1"
  val akkaHttpVersion = "10.1.11"
  val akkaVersion     = "2.6.1"
  val catsVersion     = "2.1.0"
  val tethysVersion   = "0.11.0"

  val caliban         = "com.github.ghostdogpr" %% "caliban"           % calibanVersion
  val calibanAkkaHttp = "com.github.ghostdogpr" %% "caliban-akka-http" % calibanVersion // depends on circe =C
  val calibanCats     = "com.github.ghostdogpr" %% "caliban-cats"      % calibanVersion
  val akkaHttp        = "com.typesafe.akka"     %% "akka-http"         % akkaHttpVersion
  val akkaStream      = "com.typesafe.akka"     %% "akka-stream"       % akkaVersion
  val cats            = "org.typelevel"         %% "cats-core"         % catsVersion
  val tethys          = "com.tethys-json"       %% "tethys"            % tethysVersion
}
