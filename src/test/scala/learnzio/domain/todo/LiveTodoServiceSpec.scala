package learnzio.domain.todo

import learnzio.todo.InMemoryTodoRepo
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

object LiveTodoServiceSpec extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment, Throwable] = {
    suite("TodoService Specification")(
      test("given no todos when findAll then all is empty") {
        for {
          all <- TodoService.findAll()
        } yield assertTrue(all.isEmpty)
      }
    ).provide(InMemoryTodoRepo.layer, LiveTodoService.layer)
  }
}
