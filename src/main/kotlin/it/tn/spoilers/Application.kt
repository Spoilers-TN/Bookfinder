package it.tn.spoilers

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.tn.spoilers.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
        configureSockets()
        configureRouting()
    }.start(wait = true)
}
