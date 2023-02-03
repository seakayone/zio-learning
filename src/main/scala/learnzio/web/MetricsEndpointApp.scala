package learnzio.web

import zio.*
import zio.http.*
import zio.http.model.*
import zio.metrics.connectors.prometheus.PrometheusPublisher
import zio.metrics.connectors.{MetricsConfig, prometheus}
import zio.metrics.jvm.DefaultJvmMetrics

object MetricsEndpointApp {
  def apply(): App[PrometheusPublisher] =
    Http.collectZIO[Request] { case Method.GET -> !! / "metrics" =>
      ZIO.serviceWithZIO[PrometheusPublisher](_.get.map(Response.text))
    }
}

object MetricsPrometheus {
  val layer: ZLayer[Any, Nothing, PrometheusPublisher] =
    ZLayer.make[PrometheusPublisher](
      ZLayer.succeed(MetricsConfig(interval = 5.seconds)),
      prometheus.publisherLayer,
      prometheus.prometheusLayer,
      DefaultJvmMetrics.live.unit.orDie
    )
}
