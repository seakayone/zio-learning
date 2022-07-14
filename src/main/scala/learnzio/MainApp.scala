package learnzio

import zio.{LogLevel, UIO, ZIO, ZIOAppDefault, ZLayer}
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object MainApp extends ZIOAppDefault {
  override val bootstrap: ZLayer[Any, Nothing, Unit] =
    SLF4J.slf4j(LogLevel.Info, LogFormat.colored)
  def run: UIO[Unit] = ZIO.log("Application started!")
}
