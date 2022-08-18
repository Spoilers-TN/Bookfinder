package it.tn.spoilers.plugins

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.mustache.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

fun Application.configureErrors() {
    log.info("[!] Starting Plugin - Errors.kt")
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient().get("http://whatthecommit.com/index.txt").bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.Forbidden) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.BadGateway) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.ServiceUnavailable) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.GatewayTimeout) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        status(HttpStatusCode.NotImplemented) {call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
            )
            )))
        }
        exception<Throwable> { call, cause ->
            if (cause is AuthorizationException) {
                call.respond(status= HttpStatusCode.Forbidden, MustacheContent("error.hbs", mapOf("error" to Error(
                    HttpStatusCode.Forbidden.value.toString(),
                    cause.toString(),
                    HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                )
                )))
            } else {
                call.respond(status= HttpStatusCode.InternalServerError, MustacheContent("error.hbs", mapOf("error" to Error(
                    HttpStatusCode.InternalServerError.value.toString(),
                    cause.toString(),
                    HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                )
                )))
            }
        }
    }
    log.info("[✓] Started Plugin - Errors.kt")
}

@Serializable
data class Error(val code: String, val descr: String, val meme: String)
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()