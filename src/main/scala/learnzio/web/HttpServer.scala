package learnzio.web

import zio.*
import zio.http.*

object HttpServer {
  val live: ZLayer[Any, Any, Server] = ServerConfig.live >>> Server.live
}
