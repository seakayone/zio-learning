package learnzio.todo

import learnzio.domain.todo.{NewTodo, TodoService}
import learnzio.todo.*
import zhttp.http.*
import zio.*
import zio.json.*

import java.util.UUID

object TodoApp {

  def apply(): Http[TodoService, Throwable, Request, Response] =
    Http.collectZIO[Request] {

      case Method.GET -> !! / "todos" =>
        TodoService.findAll().map(_.toJson).map(Response.json(_))

      case req @ Method.POST -> !! / "todos" =>
        req.body.asString
          .map(_.fromJson[NewTodo])
          .flatMap {
            case Right(newTodo) =>
              TodoService
                .createTodo(newTodo)
                .map(_.toJson)
                .map(Response.json(_))
            case Left(value)    =>
              ZIO.succeed(Response.text(value).setStatus(Status.BadRequest))
          }

      case Method.GET -> !! / "todos" / id =>
        TodoService.find(id).map {
          case Some(todo) => Response.json(todo.toJson)
          case None       => Response.status(Status.NotFound)
        }

      case Method.DELETE -> !! / "todos" / id =>
        TodoService.delete(id).as(Response.ok)
    }
}
