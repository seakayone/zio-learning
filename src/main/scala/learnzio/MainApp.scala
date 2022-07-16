package learnzio

import zhttp.service.Server
import zio._

object MainApp extends ZIOAppDefault {

  // Run it like any simple app
  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    ZIO.logInfo("Starting up") *> Server
      .start(8090, TodoApp())
      .provideLayer(InMemoryTodoRepo.layer)
}
