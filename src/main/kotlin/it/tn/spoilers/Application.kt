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
        log.info("[!] Starting Server - BookFinder - v2022.8.18-Alpha")
        configureRouting()
        configureAuthentication()
        configureErrors()
        configureStaticRoutes()
        configureMonitoring()
        configureSockets()
        configureHeaders()
        configureCompression()
        configureCORS()
        configurePublicFrontend()
        configurePrivateFrontend()
        log.info("[✓] Started Server - BookFinder")
    }.start(wait = true)
}