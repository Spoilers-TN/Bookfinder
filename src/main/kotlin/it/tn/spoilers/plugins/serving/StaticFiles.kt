package it.tn.spoilers.plugins.serving

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

/**
 * Class for serving the static files
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureStaticRoutes() {
    log.info("[!] Starting Plugin - StaticFiles.kt")
    routing {
        static("/assets") {
            resources("assets")
        }
    }
    log.info("[✓] Started Plugin - StaticFiles.kt")
}
