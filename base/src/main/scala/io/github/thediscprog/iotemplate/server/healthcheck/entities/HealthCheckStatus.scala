package io.github.thediscprog.iotemplate.server.healthcheck.entities

import cats.data.NonEmptyList

case class HealthCheckStatus(
    status: HealthStatus,
    details: NonEmptyList[HealthCheckerResponse]
)
