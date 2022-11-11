package it.tn.spoilers.plugins.backend

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePublicBackend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
    routing {
        post("/core/v1/version") {
            call.respondText("Hello World!")
        }
    }
}