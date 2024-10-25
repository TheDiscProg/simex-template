package io.github.thediscprog.iotemplate.server.entities

import org.http4s.server.Server

// Add additional properties as required
case class AppService[F[_]](
    server: Server
)
