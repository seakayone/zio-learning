package learnzio.todo

import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}
import zio.{ULayer, ZIO}

object InMemoryTodoRepoSpec extends ZIOSpecDefault {

  val environment: ULayer[TodoRepo] = InMemoryTodoRepo.layer
  val aTodo: Todo = Todo("id", "foo")

  def spec: Spec[TestEnvironment, Throwable] = {
    suite("TodoService Specification")(
      test("given no todos when findAll then all is empty") {
        for {
          all <- TodoRepo.findAll()
        } yield assertTrue(all.isEmpty)
      },
      test("given a todo when saving then value is saved and can be found") {
        for {
          actual <- TodoRepo.save(aTodo) *> TodoRepo.find(aTodo.id)
        } yield assertTrue(actual contains aTodo)
      },
      test(
        "given a saved todo when deleting then value is not present anymore"
      ) {
        for {
          _ <- TodoRepo.save(aTodo) *> TodoRepo.delete(aTodo.id)
          actual <- TodoRepo.find(aTodo.id)
        } yield assertTrue(actual.isEmpty)
      }
    ).provide(environment)
  }
}
