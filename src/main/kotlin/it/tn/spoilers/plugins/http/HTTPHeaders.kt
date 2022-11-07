package it.tn.spoilers.plugins.http

import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.forwardedheaders.*
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime

fun Application.configureHeaders() {
    log.info("[!] Starting Plugin - HTTPHeaders.kt")
    install(CachingHeaders) {
        options { _, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(
                    CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60),
                    ZonedDateTime.from(ZonedDateTime.now().plusDays(1))
                )

                else -> null
            }
        }
    }
    install(ConditionalHeaders)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(ForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    install(XForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    log.info("[✓] Started Plugin - HTTPHeaders.kt")
}


