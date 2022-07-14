package learnzio

import HelloWorld.sayHello
import zio.{Console, ZIO}
import zio.test._

import java.io.IOException

object HelloWorld {
  def sayHello: ZIO[Any, IOException, Unit] =
    Console.printLine("Hello, World!")
}

object HelloWorldSpec extends ZIOSpecDefault {
  def spec: Spec[Any, IOException] = suite("zio.HelloWorldSpec")(
    test("sayHello correctly displays output") {
      for {
        _ <- sayHello

        output <- TestConsole.output
      } yield assertTrue(output == Vector("Hello, World!\n"))
    }
  )
}
