package it.tn.spoilers.plugins.http

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.sentry.Sentry
import io.sentry.SentryLevel
import it.tn.spoilers.data.Error
import it.tn.spoilers.data.UserData
import it.tn.spoilers.data.user

fun Application.configureErrors() {
    log.info("[!] Starting Plugin - HTTPErrors.kt")
    install(StatusPages) {
        status(HttpStatusCode.fromValue(406)) { call, status ->
            val UserData = call.sessions.get<UserData>()
            Sentry.captureMessage("HTTP Error 406 - ${call.request.uri}")
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "error" to Error(
                            status.value.toString(),
                            status.description,
                            HttpClient().get("http://whatthecommit.com/index.txt").bodyAsText()
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        status(HttpStatusCode.NotFound) { call, status ->
            val UserData = call.sessions.get<UserData>()
            Sentry.captureMessage("HTTP Error 404 - ${call.request.uri}")
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 104 - ${call.request.uri}")
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 403 - ${call.request.uri}")
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 401 - ${call.request.uri}")
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 500 - ${call.request.uri}", SentryLevel.ERROR)
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 502 - ${call.request.uri}", SentryLevel.ERROR)
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 503 - ${call.request.uri}", SentryLevel.ERROR)
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 504 - ${call.request.uri}", SentryLevel.ERROR)
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureMessage("HTTP Error 501 - ${call.request.uri}", SentryLevel.ERROR)
            call.respond(
                status = status, PebbleContent(
                    "error.html", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
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
            Sentry.captureException(cause)
            if (cause is AuthorizationException) {
                call.respond(
                    status = HttpStatusCode.Forbidden, PebbleContent(
                        "error.html", mapOf(
                            "user" to user(
                                name = UserData?.givenName,
                                surname = UserData?.familyName,
                                photo = UserData?.picture,
                                id = UserData?.sub,
                                email = UserData?.email,
                                realm = UserData?.hd,
                                gsuite = UserData?.GSuiteUser
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
                    status = HttpStatusCode.InternalServerError, PebbleContent(
                        "error.html", mapOf(
                            "user" to user(
                                name = UserData?.givenName,
                                surname = UserData?.familyName,
                                photo = UserData?.picture,
                                id = UserData?.sub,
                                email = UserData?.email,
                                realm = UserData?.hd,
                                gsuite = UserData?.GSuiteUser
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
    log.info("[✓] Started Plugin - HTTPErrors.kt")
}

class AuthorizationException : RuntimeException()