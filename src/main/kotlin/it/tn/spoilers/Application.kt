package it.tn.spoilers

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.tn.spoilers.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", configure = {
        connectionGroupSize = 2
        workerGroupSize = 5
        callGroupSize = 10
    }) {
        log.info("[!] Starting Server - BookFinder - v2022.8.4-Alpha")
        configureErrors()
        configureStaticRoutes()
        configureRouting()
        configureMonitoring()
        configureSockets()
        configureHeaders()
        configureCompression()
        configureCORS()
        configureAuthentication()
        configurePublicFrontend()
        log.info("[✓] Started Server - BookFinder")
    }.start(wait = true)
}