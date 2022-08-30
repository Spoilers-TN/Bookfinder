package it.tn.spoilers

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.tn.spoilers.plugins.api.configureBooksApi
import it.tn.spoilers.plugins.frontend.*
import it.tn.spoilers.plugins.extras.*
import it.tn.spoilers.plugins.http.*
import it.tn.spoilers.plugins.security.*
import it.tn.spoilers.plugins.serving.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        log.info("[!] Starting Server - BookFinder - v2022.8.18-Alpha")
        configureRouting()
        configureAuthentication()
        configureErrors()
        //configureSerialization()
        configureStaticRoutes()
        configureMonitoring()
        configureSockets()
        configureHeaders()
        configureCompression()
        //configureCORS()
        configureBooksApi()
        configurePublicFrontend()
        configurePrivateFrontend()
        log.info("[✓] Started Server - BookFinder")
    }.start(wait = true)
}