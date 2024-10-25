package io.github.thediscprog.iotemplate.server.healthcheck

import cats.Applicative
import io.github.thediscprog.iotemplate.server.healthcheck.entities.{
  HealthCheckerResponse,
  HealthStatus
}

class SelfHealthCheck[F[_]: Applicative] extends HealthChecker[F] {

  override val name: String = "ServerSelfHealthCheck"

  override def checkHealth(): F[HealthCheckerResponse] =
    Applicative[F].pure(
      HealthCheckerResponse(name, HealthStatus.OK)
    )
}

object SelfHealthCheck {

  def apply[F[_]: Applicative] = new SelfHealthCheck()
}
