package io.github.thediscprog.iotemplate.server.healthcheck.entities

sealed trait HealthStatus

object HealthStatus {
  case object OK extends HealthStatus
  case object BROKEN extends HealthStatus
  case class PROBLEM(message: String) extends HealthStatus
}
