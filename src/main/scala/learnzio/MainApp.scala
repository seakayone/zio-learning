package learnzio

import learnzio.domain.todo.LiveTodoService
import learnzio.persistence.*
import learnzio.web.*
import zio.*
import zio.ZIO.*

object MainApp extends ZIOAppDefault {

  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    HttpServer.serverZio
      .provide(
        InMemoryTodoRepo.layer,
        LiveTodoService.layer,
        HttpServer.live
      )
}
