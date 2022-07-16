package learnzio

import learnzio.TodoApp.Todo
import zio.test._
import zio.{ULayer, ZIO}

object LiveTodoServiceSpec extends ZIOSpecDefault {

  val environment: ULayer[TodoService] = InMemoryTodoService.layer
  val aTodo: Todo = Todo("id", "foo")

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
          _ <- service.save(aTodo)
          actual <- service.find(aTodo.id)
        } yield assertTrue(actual contains aTodo)
      },
      test(
        "given a saved todo when deleting then value is not present anymore"
      ) {
        for {
          service <- ZIO.service[TodoService]
          _ <- service.save(aTodo) *> service.delete(aTodo)
          actual <- service.find(aTodo.id)
        } yield assertTrue(actual.isEmpty)
      }
    ).provide(environment)
  }
}
