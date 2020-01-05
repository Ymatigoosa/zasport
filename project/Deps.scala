import sbt._

object Deps {

  object Version {
    val caliban  = "0.4.1"
    val akkaHttp = "10.1.11"
    val akka     = "2.6.1"
    val cats     = "2.1.0"
    val tethys   = "0.11.0"
  }

  val caliban         = "com.github.ghostdogpr" %% "caliban"           % Version.caliban
  val calibanAkkaHttp = "com.github.ghostdogpr" %% "caliban-akka-http" % Version.caliban // depends on circe =C
  val calibanCats     = "com.github.ghostdogpr" %% "caliban-cats"      % Version.caliban
  val akkaHttp        = "com.typesafe.akka"     %% "akka-http"         % Version.akkaHttp
  val akkaStream      = "com.typesafe.akka"     %% "akka-stream"       % Version.akka
  val cats            = "org.typelevel"         %% "cats-core"         % Version.cats
  val tethys          = "com.tethys-json"       %% "tethys"            % Version.tethys
}
