package learnzio.todo

import learnzio.todo.*
import zhttp.http.*
import zio.*
import zio.json.*

object TodoApp {

  def apply(): Http[TodoRepo, Throwable, Request, Response] =
    Http.fromZIO(ZIO.service[TodoRepo]).flatMap { todos =>
      Http.collectZIO[Request] {
        case Method.GET -> !! / "todos" =>
          todos.findAll().map(_.toJson).map(Response.json(_))

        case req @ Method.POST -> !! / "todos" =>
          req.bodyAsString
            .map(_.fromJson[Todo])
            .flatMap {
              case Right(t) => todos.save(t).map(_ => Response.ok)
              case Left(value) =>
                ZIO.succeed(Response.text(value).setStatus(Status.BadRequest))
            }

        case Method.GET -> !! / "todos" / id =>
          todos.find(id).map {
            case Some(todo) => Response.json(todo.toJson)
            case None       => Response.status(Status.NotFound)
          }

        case Method.DELETE -> !! / "todos" / id =>
          todos.delete(id).map(_ => Response.ok)
      }
    }
}
