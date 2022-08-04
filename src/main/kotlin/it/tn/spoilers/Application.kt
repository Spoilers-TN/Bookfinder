package it.tn.spoilers

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.tn.spoilers.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        log.info("[!] Starting Server - BookFinder - v2022.8.4-Alpha")
        configureErrors()
        configureCompression()
        configureHeaders()
        configureStaticRoutes()
        configureCORS()
        configureFrontend()
        configureAuthentication()
        configureMonitoring()
        configureSerialization()
        configureSockets()
        configureRouting()
        log.info("[✓] Started Server - BookFinder")
    }.start(wait = true)
}