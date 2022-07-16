package learnzio.todo

import learnzio.todo.*
import zhttp.http.*
import zio.*
import zio.json.*

object TodoApp {

  def apply(): Http[TodoRepo, Throwable, Request, Response] =
    Http.collectZIO[Request] {

      case Method.GET -> !! / "todos" =>
        TodoRepo.findAll().map(_.toJson).map(Response.json(_))

      case req @ Method.POST -> !! / "todos" =>
        req.bodyAsString
          .map(_.fromJson[Todo])
          .flatMap {
            case Right(t) => TodoRepo.save(t).map(_ => Response.ok)
            case Left(value) =>
              ZIO.succeed(Response.text(value).setStatus(Status.BadRequest))
          }

      case Method.GET -> !! / "todos" / id =>
        TodoRepo.find(id).map {
          case Some(todo) => Response.json(todo.toJson)
          case None       => Response.status(Status.NotFound)
        }

      case Method.DELETE -> !! / "todos" / id =>
        TodoRepo.delete(id).map(_ => Response.ok)
    }
}
