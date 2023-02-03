package learnzio

import zio.*
import zio.ZIO.*
import zio.http.*
import zio.http.model.*

object MainApp extends ZIOAppDefault {

  private val serverLayer: ZLayer[Any, Throwable, Server] =
    (ServerConfig.live >>> Server.live).debug

  private val helloApp: App[Any] =
    Http.collect[Request] { case Method.GET -> !! / "hello" =>
      Response.text("World")
    }

  private val program =
    ZIO.logInfo("Starting up")
      *> ZIO
        .serviceWithZIO[Server](_.install(helloApp))
      *> ZIO.never

  override val run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    program.debug
      .provideSomeLayer[Any](serverLayer)
}
