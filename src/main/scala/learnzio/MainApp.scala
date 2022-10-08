package learnzio

import learnzio.domain.todo.LiveTodoService
import learnzio.todo.*
import learnzio.web.TodoApp
import zhttp.service.Server
import zio.*

object MainApp extends ZIOAppDefault {

  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    ZIO.logInfo("Starting up") *>
      Server
        .start(8090, TodoApp())
        .provide(InMemoryTodoRepo.layer, LiveTodoService.layer)
}
