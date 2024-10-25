package io.github.thediscprog.iotemplate.server

import cats.data.NonEmptyList
import cats.effect.{Async, Resource}
import cats.{Monad, MonadError, Parallel}
import com.comcast.ip4s.*
import io.circe.config.parser
import io.github.thediscprog.iotemplate.guardrail.healthcheck.HealthcheckResource
import io.github.thediscprog.iotemplate.server.entities.AppService
import io.github.thediscprog.iotemplate.server.healthcheck.{
  HealthCheckService,
  HealthChecker,
  HealthcheckAPIHandler,
  SelfHealthCheck
}
import io.github.thediscprog.shareprice.config.ServerConfiguration
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger
import org.typelevel.log4cats.Logger as Log4CatsLogger

class AppServer {

  def createServer[F[_]: Monad: Async: Log4CatsLogger: Parallel](implicit
      F: MonadError[F, Throwable]
  ): Resource[F, AppService[F]] =
    for {
      conf <- Resource.eval(parser.decodePathF[F, ServerConfiguration](path = "server"))

      // Health checkers
      checkers = NonEmptyList.of[HealthChecker[F]](SelfHealthCheck[F])
      healthCheckers = HealthCheckService(checkers)
      healthRoutes = new HealthcheckResource().routes(
        new HealthcheckAPIHandler[F](healthCheckers)
      )

      // Routes and HTTP App
      allRoutes = healthRoutes.orNotFound
      httpApp = Logger.httpApp(logHeaders = true, logBody = true)(allRoutes)

      // Build server
      httpPort = Port.fromInt(conf.http.port)
      httpHost = Ipv4Address.fromString(conf.http.hostAddress)
      server <- EmberServerBuilder
        .default[F]
        .withPort(httpPort.getOrElse(port"8000"))
        .withHost(httpHost.getOrElse(ipv4"0.0.0.0"))
        .withHttpApp(httpApp)
        .build

    } yield AppService(server)
}
