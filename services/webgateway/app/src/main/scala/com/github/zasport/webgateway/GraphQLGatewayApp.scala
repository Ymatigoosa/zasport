package com.github.zasport.webgateway

import zio.App
import zio.console.putStrLn

object GraphQLGatewayApp extends App {

  def run(args: List[String]) =
    myAppLogic.map(_ => 0)

  val myAppLogic =
    for {
      _ <- putStrLn("Hello World")
    } yield ()
}