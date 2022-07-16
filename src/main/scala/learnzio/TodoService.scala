package learnzio

import learnzio.TodoApp.Todo
import zio._

trait TodoService {
  def find(id: String): Task[Option[Todo]]
  def findAll(): Task[List[Todo]]
  def save(todo: Todo): Task[Unit]
  def delete(todo: Todo): Task[Unit]
}

object InMemoryTodoService {
  val layer: ULayer[TodoService] = ZLayer.fromZIO(
    Ref
      .make(Map.empty[String, Todo])
      .map(new InMemoryTodoService(_))
  )
}

case class InMemoryTodoService(todos: Ref[Map[String, Todo]])
    extends TodoService {

  override def find(id: String): ZIO[Any, Nothing, Option[Todo]] =
    todos.get.map(_.get(id))

  override def findAll(): Task[List[Todo]] = todos.get.map(_.values.toList)

  override def save(todo: Todo): Task[Unit] =
    todos.updateAndGet(_ + (todo.id -> todo)).unit

  override def delete(todo: Todo): Task[Unit] =
    todos.updateAndGet(_ - todo.id).unit
}
