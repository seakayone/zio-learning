package learnzio.todo

import zio.json.*

case class Todo(id: String, title: String) {}

object Todo {
  implicit val encoder: JsonEncoder[Todo] = DeriveJsonEncoder.gen[Todo]
  implicit val decoder: JsonDecoder[Todo] = DeriveJsonDecoder.gen[Todo]
}
