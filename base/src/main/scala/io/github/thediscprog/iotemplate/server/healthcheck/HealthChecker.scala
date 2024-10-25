package io.github.thediscprog.iotemplate.server.healthcheck

import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthCheckerResponse

trait HealthChecker[F[_]] {

  val name: String

  def checkHealth(): F[HealthCheckerResponse]

}
