package io.github.thediscprog.iotemplate.server

import cats.effect.*
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import scala.language.postfixOps

object MainApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      given Logger[IO] <- Resource.eval(Slf4jLogger.create[IO])
      appService <- (new AppServer()).createServer[IO]
    } yield ExitCode.Success)
      .use(_ => IO.never)

}
