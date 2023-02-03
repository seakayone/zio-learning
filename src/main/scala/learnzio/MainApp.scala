package learnzio

import learnzio.domain.todo.{TodoService, TodoServiceLive}
import learnzio.persistence.*
import learnzio.web.*
import zio.*
import zio.ZIO.*
import zio.http.*
import zio.http.model.*
import zio.metrics.connectors.prometheus.PrometheusPublisher
import zio.metrics.connectors.{MetricsConfig, prometheus}
import zio.metrics.jvm.DefaultJvmMetrics

object MainApp extends ZIOApp {
  private val httpApps = TodoApp() ++ MetricsEndpointApp()

  override val environmentTag: EnvironmentTag[Environment] =
    EnvironmentTag[Environment]

  override type Environment = Server with TodoService with PrometheusPublisher

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    ZLayer.make[Server with TodoService with PrometheusPublisher](
      HttpServer.live,
      TodoRepoLive.layer,
      TodoServiceLive.layer,
      MetricsPrometheus.layer
    )

  private val program = ZIO.logInfo("Starting up") *> Server.serve(httpApps)

  override val run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    program
}
