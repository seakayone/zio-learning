package learnzio

import learnzio.TodoApp.Todo
import zio._

import scala.collection.mutable

trait TodoService {
  def find(id: String): Task[Option[Todo]]
}

object LiveTodoService {
  val layer: ULayer[TodoService] = ZLayer.fromZIO(
    Ref
      .make {
        val map = mutable.Map.empty[String, Todo]
        map.put("42", Todo("42", "answer"))
        map
      }
      .map(new LiveTodoService(_))
  )
}

case class LiveTodoService(todos: Ref[mutable.Map[String, Todo]])
    extends TodoService {
  override def find(id: String): ZIO[Any, Nothing, Option[Todo]] =
    todos.get.map(_.get(id))
}
