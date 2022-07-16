package learnzio

import zhttp.http.Method.{DELETE, GET, POST}
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

  def apply(): Http[TodoRepo, Throwable, Request, Response] =
    Http.fromZIO(ZIO.service[TodoRepo]).flatMap { todos =>
      Http.collectZIO[Request] {
        case GET -> _ / "todos" =>
          todos.findAll().map(_.toJson).map(Response.json(_))

        case req @ POST -> _ / "todos" =>
          req.bodyAsString
            .map(_.fromJson[Todo])
            .flatMap {
              case Right(t) => todos.save(t).map(_ => Response.ok)
              case Left(value) =>
                ZIO.succeed(Response.text(value).setStatus(Status.BadRequest))
            }

        case GET -> _ / "todos" / id =>
          todos.find(id).map {
            case Some(todo) => Response.json(todo.toJson)
            case None       => Response.status(NotFound)
          }

        case DELETE -> _ / "todos" / id =>
          todos.delete(id).map(_ => Response.ok)
      }
    }
}
