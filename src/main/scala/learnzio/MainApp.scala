package learnzio

import learnzio.domain.todo.LiveTodoService
import learnzio.persistence.*
import learnzio.web.*
import zio.*
import zio.ZIO.*
import zio.http.Server
import zio.metrics.connectors.{MetricsConfig, prometheus}
import zio.metrics.jvm.DefaultJvmMetrics

object MainApp extends ZIOAppDefault {

  private val httpApps = TodoApp() ++ MetricsEndpointApp()

  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    ZIO.logInfo("Starting up") *>
      Server
        .serve(httpApps)
        .provide(
          InMemoryTodoRepo.layer,
          LiveTodoService.layer,
          HttpServer.live,
          MetricsPrometheus.layer
        )
}
