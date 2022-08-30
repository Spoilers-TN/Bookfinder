package it.tn.spoilers.plugins.serving

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    log.info("[!] Starting Plugin - Routing.kt")
    install(AutoHeadResponse)

    routing {
        get("/teapot") {
            call.respondText(text = "418: I'm a teapot \uD83E\uDED6", status = HttpStatusCode.fromValue(418))
        }
    }
    log.info("[✓] Started Plugin - Routing.kt")
}
