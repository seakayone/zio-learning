package learnzio.todo

import learnzio.todo.*
import zio.*

import java.util.UUID

case class LiveTodoService(todoRepo: TodoRepo) extends TodoService {
  override def createTodo(newTodo: NewTodo): Task[Todo] =
    todoRepo.save(Todo(UUID.randomUUID().toString, newTodo.title))
}

object LiveTodoService {
  val layer: ZLayer[TodoRepo, Nothing, LiveTodoService] =
    ZLayer.fromZIO(ZIO.service[TodoRepo].map(new LiveTodoService(_)))
}
