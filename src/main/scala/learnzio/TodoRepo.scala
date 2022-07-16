package learnzio

import learnzio.TodoApp.Todo
import zio._

trait TodoRepo {
  def find(id: String): Task[Option[Todo]]
  def findAll(): Task[Iterable[Todo]]
  def save(todo: Todo): Task[Unit]
  def delete(id: String): Task[Unit]
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

  override def save(todo: Todo): Task[Unit] =
    todos.updateAndGet(_ + (todo.id -> todo)).unit

  override def delete(id: String): Task[Unit] =
    todos.updateAndGet(_ - id).unit
}
