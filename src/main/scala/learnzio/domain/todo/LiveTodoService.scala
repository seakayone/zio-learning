package learnzio.domain.todo

import learnzio.persistence.*
import zio.*

import java.util.UUID

case class LiveTodoService(todoRepo: TodoRepo) extends TodoService {
  override def createTodo(newTodo: NewTodo): Task[Todo] =
    todoRepo.save(Todo(UUID.randomUUID().toString, newTodo.title))

  override def find(id: String): Task[Option[Todo]] = todoRepo.find(id)

  override def findAll(): Task[Iterable[Todo]] = todoRepo.findAll()

  override def delete(id: String): Task[Unit] = todoRepo.delete(id)
}

object LiveTodoService {
  val layer: ZLayer[TodoRepo, Nothing, LiveTodoService] =
    ZLayer.fromZIO(ZIO.service[TodoRepo].map(new LiveTodoService(_)))
}
