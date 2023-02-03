package learnzio.domain.todo

import learnzio.persistence.TodoRepoLive
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

object TodoServiceLiveSpec$ extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment, Throwable] = {
    suite("TodoService Specification")(
      test("given no todos when findAll then all is empty") {
        for {
          all <- TodoService.findAll()
        } yield assertTrue(all.isEmpty)
      }
    ).provide(TodoRepoLive.layer, TodoServiceLive.layer)
  }
}
