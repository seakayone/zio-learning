package learnzio.persistence

import learnzio.domain.todo.Todo
import learnzio.persistence.*
import zio.*

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

object InMemoryTodoRepo {
  val layer: ULayer[TodoRepo] = ZLayer.fromZIO(
    Ref
      .make(Map.empty[String, Todo])
      .map(new InMemoryTodoRepo(_))
  )
}
