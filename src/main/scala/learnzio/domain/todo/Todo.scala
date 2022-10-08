package learnzio.domain.todo

import zio.json.*

case class Todo(id: String, title: String)

object Todo {
  implicit val encoder: JsonEncoder[Todo] = DeriveJsonEncoder.gen[Todo]
}
