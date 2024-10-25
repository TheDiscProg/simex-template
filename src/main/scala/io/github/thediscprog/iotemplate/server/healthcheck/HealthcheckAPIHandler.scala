package io.github.thediscprog.iotemplate.server.healthcheck

import cats.Functor
import cats.implicits._
import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthCheckStatus
import io.github.thediscprog.iotemplate.guardrail.definitions.HealthResponse
import io.github.thediscprog.iotemplate.guardrail.healthcheck.HealthcheckHandler
import io.github.thediscprog.iotemplate.guardrail.healthcheck.HealthcheckResource.HealthcheckResponse
import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthStatus.OK

class HealthcheckAPIHandler[F[_]: Functor](checker: HealthCheckAlgebra[F])
    extends HealthcheckHandler[F] {
  override def healthcheck(
      respond: HealthcheckResponse.type
  )(): F[HealthcheckResponse] =
    for {
      health <- checker.checkHealth
      response = health match {
        case HealthCheckStatus(OK, _) =>
          HealthcheckResponse.Ok(HealthResponse(HealthResponse.ServiceStatus.Ok))
        case _ => HealthcheckResponse.Ok(HealthResponse(HealthResponse.ServiceStatus.Broken))
      }
    } yield response
}
