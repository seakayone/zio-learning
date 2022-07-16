package learnzio

import zhttp.http.Method.GET
import zhttp.http.Status.NotFound
import zhttp.http._
import zio.ZIO
import zio.json._

object TodoApp {

  case class Todo(id: String, title: String) {}

  object Todo {
    implicit val encoder: JsonEncoder[Todo] = DeriveJsonEncoder.gen[Todo]
    implicit val decoder: JsonDecoder[Todo] = DeriveJsonDecoder.gen[Todo]
  }

  def apply(): Http[TodoService, Throwable, Request, Response] =
    Http.fromZIO(ZIO.service[TodoService]).flatMap { todos =>
      Http.collectZIO[Request] {
        case GET -> _ / "todos" =>
          todos.findAll().map(l => Response.json(l.toJson))
        case GET -> _ / "todos" / id =>
          todos.find(id).map {
            case Some(user) => Response.json(user.toJson)
            case None       => Response.status(NotFound)
          }
      }
    }
}
