package learnzio.web

import zio.*
import zio.http.*

object HttpServer {
  val live: ZLayer[Any, Throwable, Server] = ServerConfig.live >>> Server.live
}
