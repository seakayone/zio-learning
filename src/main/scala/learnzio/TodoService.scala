package learnzio

import learnzio.TodoApp.Todo
import zio._

trait TodoService {
  def find(id: String): Task[Option[Todo]]
}

object LiveTodoService {
  val layer: ULayer[TodoService] = ZLayer.succeed(new LiveTodoService)
}

final class LiveTodoService extends TodoService {
  override def find(id: String): Task[Option[Todo]] =
    ZIO.succeed(Some(Todo("id", id)))
}
