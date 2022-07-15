package learnzio

import zhttp.http.Method.GET
import zhttp.http.Status.NotFound
import zhttp.http._
import zio.ZIO

object TodoApp {

  case class Todo(id: String, title: String) {
    def toJson = s"{\"id\": \"$id\",\"title\": \"$title\"}"
  }

//  object Todo {
//    implicit val decoder: JsonEncoder[Todo] = DeriveJsonEncoder.gen[Todo]
//  }

  def apply(): Http[TodoService, Throwable, Request, Response] =
    Http.fromZIO(ZIO.service[TodoService]).flatMap { todos =>
      Http.collectZIO[Request] {
        case GET -> !! / "todo" =>
          ZIO.succeed(Response.json("{\"hello\":\"json\"}"))
        case GET -> _ / "todo" / id =>
          todos
            .find(id)
            .map(
              _.map(_.toJson)
                .map(Response.json(_))
                .getOrElse(Response.status(NotFound))
            )
      }
    }
}
