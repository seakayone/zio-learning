package learnzio

import zhttp.http._
import zhttp.service.Server
import zio._

object MainApp extends ZIOAppDefault {

  // Create HTTP route
  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "text" => Response.text("Hello World!")
    case Method.GET -> !! / "json" =>
      Response.json("""{"greetings": "Hello World!"}""")
  }

  // Run it like any simple app
  override val run: ZIO[Any with ZIOAppArgs with Scope, Throwable, Nothing] =
    Server.start(8090, app)
}
