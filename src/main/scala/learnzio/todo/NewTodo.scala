package learnzio.todo

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class NewTodo(title: String)

object NewTodo {
//  implicit val encoder: JsonEncoder[NewTodo] = DeriveJsonEncoder.gen[NewTodo]
  implicit val decoder: JsonDecoder[NewTodo] = DeriveJsonDecoder.gen[NewTodo]
}
