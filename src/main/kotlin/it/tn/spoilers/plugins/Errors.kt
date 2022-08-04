package it.tn.spoilers.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrors() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Not Found", status = HttpStatusCode.NotFound)
        }
        status(HttpStatusCode.Forbidden) { call, status ->
            call.respondText(text = "403: Forbidden", status = HttpStatusCode.Forbidden)
        }
        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respondText(text = "500: Internal Server Error", status = HttpStatusCode.InternalServerError)
        }
        status(HttpStatusCode.BadGateway) { call, status ->
            call.respondText(text = "502: Bad Gateway", status = HttpStatusCode.BadGateway)
        }
        status(HttpStatusCode.ServiceUnavailable) { call, status ->
            call.respondText(text = "503: Service Unavailable", status = HttpStatusCode.ServiceUnavailable)
        }
        status(HttpStatusCode.GatewayTimeout) { call, status ->
            call.respondText(text = "504: Gateway Timeout", status = HttpStatusCode.GatewayTimeout)
        }
        exception<Throwable> { call, cause ->
            if (cause is AuthorizationException) {
                call.respondText(text = "403: $cause", status = HttpStatusCode.Forbidden)
            } else {
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()