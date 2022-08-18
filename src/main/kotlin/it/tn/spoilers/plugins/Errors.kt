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

suspend fun ReturnError(): String {
    return HttpClient(Apache).get("http://whatthecommit.com/index.txt") {
    }.bodyAsText()
}

fun Application.configureErrors() {
    log.info("[!] Starting Plugin - Errors.kt")
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.Forbidden) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.BadGateway) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.ServiceUnavailable) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.GatewayTimeout) { call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        status(HttpStatusCode.NotImplemented) {call, status ->
            call.respond(status= status, MustacheContent("error.hbs", mapOf("error" to Error(
                status.value.toString(),
                status.description,
                ReturnError()
            )
            )))
        }
        exception<Throwable> { call, cause ->
            if (cause is AuthorizationException) {
                call.respond(status= HttpStatusCode.Forbidden, MustacheContent("error.hbs", mapOf("error" to Error(
                    HttpStatusCode.Forbidden.value.toString(),
                    cause.toString(),
                    ReturnError()
                )
                )))
            } else {
                call.respond(status= HttpStatusCode.InternalServerError, MustacheContent("error.hbs", mapOf("error" to Error(
                    HttpStatusCode.InternalServerError.value.toString(),
                    cause.toString(),
                    ReturnError()
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