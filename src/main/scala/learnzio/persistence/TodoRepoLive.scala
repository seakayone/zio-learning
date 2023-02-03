package learnzio.persistence

import learnzio.domain.todo.Todo
import learnzio.persistence.*
import zio.*

case class TodoRepoLive(todos: Ref[Map[String, Todo]]) extends TodoRepo {

  override def find(id: String): UIO[Option[Todo]] =
    todos.get.map(_.get(id))

  override def findAll(): UIO[Iterable[Todo]] =
    todos.get.map(_.values)

  override def save(todo: Todo): UIO[Todo] =
    todos.updateAndGet(_ + (todo.id -> todo)) *> todos.get.map(
      _(todo.id)
    )

  override def delete(id: String): UIO[Unit] =
    todos.updateAndGet(_ - id).unit
}

object TodoRepoLive {
  val layer: ULayer[TodoRepo] = ZLayer.fromZIO(
    Ref
      .make(Map.empty[String, Todo])
      .map(new TodoRepoLive(_))
  )
}
