package learnzio.todo

import zio.json.*

case class NewTodo(title: String)

object NewTodo {
  implicit val decoder: JsonDecoder[NewTodo] = DeriveJsonDecoder.gen[NewTodo]
}
