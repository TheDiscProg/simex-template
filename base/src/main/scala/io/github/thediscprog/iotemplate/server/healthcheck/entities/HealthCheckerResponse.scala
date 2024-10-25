package io.github.thediscprog.iotemplate.server.healthcheck.entities

case class HealthCheckerResponse(
    name: String,
    status: HealthStatus
)
