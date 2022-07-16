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

object InMemoryTodoRepo {
  val layer: ULayer[TodoRepo] = ZLayer.fromZIO(
    Ref
      .make(Map.empty[String, Todo])
      .map(new InMemoryTodoRepo(_))
  )
}

case class InMemoryTodoRepo(todos: Ref[Map[String, Todo]]) extends TodoRepo {

  override def find(id: String): Task[Option[Todo]] =
    todos.get.map(_.get(id))

  override def findAll(): Task[Iterable[Todo]] =
    todos.get.map(_.values)

  override def save(todo: Todo): Task[Todo] =
    todos.updateAndGet(_ + (todo.id -> todo)) *> todos.get.map(
      _(todo.id)
    )

  override def delete(id: String): Task[Unit] =
    todos.updateAndGet(_ - id).unit
}
