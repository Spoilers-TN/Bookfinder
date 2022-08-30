package it.tn.spoilers.plugins.security

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    log.info("[!] Starting Plugin - CORSSecurity.kt")
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
    }
    log.info("[✓] Started Plugin - CORSSecurity.kt")
}