package learnzio.domain.todo

import learnzio.persistence.*
import zio.*

import java.util.UUID

case class TodoServiceLive(todoRepo: TodoRepo) extends TodoService {
  override def createTodo(newTodo: NewTodo): UIO[Todo] =
    todoRepo.save(Todo(UUID.randomUUID().toString, newTodo.title))

  override def find(id: String): UIO[Option[Todo]] = todoRepo.find(id)

  override def findAll(): UIO[Iterable[Todo]] = todoRepo.findAll()

  override def delete(id: String): UIO[Unit] = todoRepo.delete(id)
}

object TodoServiceLive {
  val layer: URLayer[TodoRepo, TodoService] =
    ZLayer.fromFunction(TodoServiceLive.apply _)
}
