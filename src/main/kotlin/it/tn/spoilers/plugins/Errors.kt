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
import io.ktor.server.sessions.*

fun Application.configureErrors() {
    log.info("[!] Starting Plugin - Errors.kt")
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient().get("http://whatthecommit.com/index.txt").bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.fromValue(104)) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient().get("http://whatthecommit.com/index.txt").bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.Forbidden) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.Unauthorized) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.InternalServerError) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.BadGateway) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.ServiceUnavailable) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.GatewayTimeout) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.NotImplemented) { call, status ->
            val UserData = call.sessions.get<UserData>()
            call.respond(
                status = status, MustacheContent(
                    "error.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        exception<Throwable> { call, cause ->
            val UserData = call.sessions.get<UserData>()
            if (cause is AuthorizationException) {
                call.respond(
                    status = HttpStatusCode.Forbidden, MustacheContent(
                        "error.hbs", mapOf(
                            "user" to user(
                                name = UserData?.givenName,
                                surname = UserData?.familyName,
                                photo = UserData?.picture,
                                id = UserData?.sub,
                                email = UserData?.email,
                                realm = UserData?.hd
                            ), "error" to Error(
                                HttpStatusCode.Forbidden.value.toString(),
                                cause.toString(),
                                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                            ), "logged" to (call.sessions.get<UserData>() != null)
                        )
                    )
                )
            } else {
                call.respond(
                    status = HttpStatusCode.InternalServerError, MustacheContent(
                        "error.hbs", mapOf(
                            "user" to user(
                                name = UserData?.givenName,
                                surname = UserData?.familyName,
                                photo = UserData?.picture,
                                id = UserData?.sub,
                                email = UserData?.email,
                                realm = UserData?.hd
                            ), "error" to Error(
                                HttpStatusCode.InternalServerError.value.toString(),
                                cause.toString(),
                                HttpClient(Apache).get("http://whatthecommit.com/index.txt") {}.bodyAsText()
                            ), "logged" to (call.sessions.get<UserData>() != null)
                        )
                    )
                )
            }
        }
    }
    log.info("[✓] Started Plugin - Errors.kt")
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()