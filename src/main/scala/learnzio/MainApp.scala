package learnzio

import learnzio.todo.{InMemoryTodoRepo, TodoApp}
import zhttp.service.Server
import zio.*

object MainApp extends ZIOAppDefault {

  // Run it like any simple app
  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    ZIO.logInfo("Starting up") *>
      Server
        .start(8090, TodoApp())
        .provideLayer(InMemoryTodoRepo.layer)
}
