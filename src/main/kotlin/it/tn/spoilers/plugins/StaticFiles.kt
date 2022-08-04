package it.tn.spoilers.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*

fun Application.configureStaticRoutes() {
    log.info("[!] Starting Plugin - StaticFiles.kt")
    routing {
        static("/assets") {
            resources("assets")
        }
    }
    log.info("[✓] Started Plugin - StaticFiles.kt")
}
