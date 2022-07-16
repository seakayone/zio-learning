package learnzio

import learnzio.TodoApp.Todo
import zio._

import scala.collection.mutable

trait TodoService {
  def save(todo: Todo): Task[Unit]
  def find(id: String): Task[Option[Todo]]
  def findAll(): Task[List[Todo]]
}

object InMemoryTodoService {
  val layer: ULayer[TodoService] = ZLayer.fromZIO(
    Ref
      .make(mutable.Map.empty[String, Todo])
      .map(new InMemoryTodoService(_))
  )
}

case class InMemoryTodoService(todos: Ref[mutable.Map[String, Todo]])
    extends TodoService {
  override def find(id: String): ZIO[Any, Nothing, Option[Todo]] =
    todos.get.map(_.get(id))

  override def findAll(): Task[List[Todo]] = todos.get.map(_.values.toList)

  override def save(todo: Todo): ZIO[Any, Nothing, Unit] =
    todos
      .updateAndGet(value => {
        value.put(todo.id, todo)
        value
      })
      .unit
}
