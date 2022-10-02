package learnzio.todo

import learnzio.todo.*
import zio.*

trait TodoRepo {
  def find(id: String): Task[Option[Todo]]
  def findAll(): Task[Iterable[Todo]]
  def save(todo: Todo): Task[Todo]
  def delete(id: String): Task[Unit]
}

object TodoRepo {
  def find(id: String): ZIO[TodoRepo, Throwable, Option[Todo]] =
    ZIO.service[TodoRepo].flatMap(_.find(id))

  def findAll(): ZIO[TodoRepo, Throwable, Iterable[Todo]] =
    ZIO.service[TodoRepo].flatMap(_.findAll())

  def save(todo: Todo): ZIO[TodoRepo, Throwable, Todo] =
    ZIO.service[TodoRepo].flatMap(_.save(todo))

  def delete(id: String): ZIO[TodoRepo, Throwable, Unit] =
    ZIO.service[TodoRepo].flatMap(_.delete(id))
}
