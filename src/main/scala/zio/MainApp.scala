package zio

import zhttp.http.Http
import zhttp.service.Server
import zio.ZIOAppDefault
import zio.counter.CounterApp

object MainApp extends ZIOAppDefault {
  def run = Server
    .start(port = 8090, http = CounterApp())
    .provide(
      // An layer responsible for storing the state of the `counterApp`
      ZLayer.fromZIO(Ref.make(0))
    )
    .exitCode
}
