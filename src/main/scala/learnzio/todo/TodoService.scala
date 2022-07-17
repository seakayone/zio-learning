package learnzio.todo

import learnzio.todo.*
import zio.*

import java.util.UUID

trait TodoService {
  def createTodo(newTodo: NewTodo): Task[Todo]
}

object TodoService {
  def createTodo(newTodo: NewTodo): ZIO[TodoService, Throwable, Todo] =
    ZIO.service[TodoService].flatMap(_.createTodo(newTodo))
}
