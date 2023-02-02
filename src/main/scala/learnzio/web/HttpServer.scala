package learnzio.web

import zio.*
import zio.http.*

object HttpServer {

  val serverZio = Server.serve(TodoApp())

  val live: TaskLayer[Server]       =
    ServerConfig.live >>> Server.live

}
