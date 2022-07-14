package zio

import zio.HelloWorld.sayHello
import zio.test._

import java.io.IOException

object HelloWorld {
  def sayHello: ZIO[Any, IOException, Unit] =
    Console.printLine("Hello, World!")
}

object HelloWorldSpec extends ZIOSpecDefault {
  def spec = suite("zio.HelloWorldSpec")(
    test("sayHello correctly displays output") {
      for {
        _ <- sayHello

        output <- TestConsole.output
      } yield assertTrue(output == Vector("Hello, World!\n"))
    }
  )
}
