package learnzio.persistence

import learnzio.domain.todo.Todo
import learnzio.persistence.*
import zio.*

trait TodoRepo {
  def find(id: String): UIO[Option[Todo]]
  def findAll(): UIO[Iterable[Todo]]
  def save(todo: Todo): UIO[Todo]
  def delete(id: String): UIO[Unit]
}

object TodoRepo {
  def find(id: String): URIO[TodoRepo, Option[Todo]] =
    ZIO.service[TodoRepo].flatMap(_.find(id))

  def findAll(): URIO[TodoRepo, Iterable[Todo]] =
    ZIO.service[TodoRepo].flatMap(_.findAll())

  def save(todo: Todo): URIO[TodoRepo, Todo] =
    ZIO.service[TodoRepo].flatMap(_.save(todo))

  def delete(id: String): URIO[TodoRepo, Unit] =
    ZIO.service[TodoRepo].flatMap(_.delete(id))
}
