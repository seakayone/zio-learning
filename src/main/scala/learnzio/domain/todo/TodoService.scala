package learnzio.domain.todo

import learnzio.persistence.*
import zio.*

import java.util.UUID

trait TodoService {
  def createTodo(newTodo: NewTodo): UIO[Todo]
  def find(id: String): UIO[Option[Todo]]
  def findAll(): UIO[Iterable[Todo]]
  def delete(id: String): UIO[Unit]
}

object TodoService {
  def createTodo(newTodo: NewTodo): URIO[TodoService, Todo] =
    ZIO.service[TodoService].flatMap(_.createTodo(newTodo))

  def find(id: String): URIO[TodoService, Option[Todo]] =
    ZIO.service[TodoService].flatMap(_.find(id))

  def findAll(): URIO[TodoService, Iterable[Todo]] =
    ZIO.service[TodoService].flatMap(_.findAll())

  def delete(id: String): URIO[TodoService, Unit] =
    ZIO.service[TodoService].flatMap(_.delete(id))
}
