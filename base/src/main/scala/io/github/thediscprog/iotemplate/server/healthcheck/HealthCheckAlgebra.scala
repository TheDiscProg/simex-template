package io.github.thediscprog.iotemplate.server.healthcheck

import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthCheckStatus

trait HealthCheckAlgebra[F[_]] {

  def checkHealth: F[HealthCheckStatus]
}
