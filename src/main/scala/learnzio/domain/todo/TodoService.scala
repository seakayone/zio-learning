package learnzio.domain.todo

import learnzio.persistence.*
import zio.*

import java.util.UUID

trait TodoService {
  def createTodo(newTodo: NewTodo): Task[Todo]
  def find(id: String): Task[Option[Todo]]
  def findAll(): Task[Iterable[Todo]]
  def delete(id: String): Task[Unit]
}

object TodoService {
  def createTodo(newTodo: NewTodo): ZIO[TodoService, Throwable, Todo] =
    ZIO.service[TodoService].flatMap(_.createTodo(newTodo))

  def find(id: String): ZIO[TodoService, Throwable, Option[Todo]] =
    ZIO.service[TodoService].flatMap(_.find(id))

  def findAll(): ZIO[TodoService, Throwable, Iterable[Todo]] =
    ZIO.service[TodoService].flatMap(_.findAll())

  def delete(id: String): ZIO[TodoService, Throwable, Unit] =
    ZIO.service[TodoService].flatMap(_.delete(id))
}
