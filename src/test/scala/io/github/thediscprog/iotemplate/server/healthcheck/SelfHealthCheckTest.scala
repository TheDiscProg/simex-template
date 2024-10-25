package io.github.thediscprog.iotemplate.server.healthcheck

import cats.Id
import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthCheckerResponse
import io.github.thediscprog.iotemplate.server.healthcheck.entities.HealthStatus.OK
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SelfHealthCheckTest extends AnyFlatSpec with Matchers {

  val sut = new SelfHealthCheck[Id]

  it should "return OK health status" in {
    val status: Id[HealthCheckerResponse] = sut.checkHealth()

    status.status shouldBe OK
  }

}
