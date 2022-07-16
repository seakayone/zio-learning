package learnzio

import learnzio.TodoApp.Todo
import zio.test._
import zio.{ULayer, ZIO}

object LiveTodoServiceSpec extends ZIOSpecDefault {

  val environment: ULayer[TodoService] = InMemoryTodoService.layer

  def spec: Spec[TestEnvironment, Throwable] = {
    suite("TodoService Specification")(
      test("given no todos when findAll then all is empty") {
        for {
          service <- ZIO.service[TodoService]
          all <- service.findAll()
        } yield assertTrue(all.isEmpty)
      },
      test("given a todo when saving then value is saved and can be found") {
        for {
          service <- ZIO.service[TodoService]
          todo = Todo("id", "foo")
          actual <- service.save(todo) *> service.find(todo.id)
        } yield assertTrue(actual contains todo)
      }
    ).provide(environment)
  }
}
