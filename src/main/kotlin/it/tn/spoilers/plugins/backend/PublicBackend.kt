package it.tn.spoilers.plugins.backend

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
/**
 * Function containing the pages and routing for the public backend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.11.11
 */
fun Application.configurePublicBackend() {
    log.info("[!] Starting Plugin - PublicBackend.kt")
    routing {
        get("/core/v1/version") {
            call.respondText("Bookfinder - 2022.11.11")
        }
    }
}