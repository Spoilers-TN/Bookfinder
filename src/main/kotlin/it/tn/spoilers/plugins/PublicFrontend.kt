package it.tn.spoilers.plugins

import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.server.application.*
import io.ktor.server.mustache.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configurePublicFrontend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    routing {
        get("/") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "index.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        get("/index") {
            call.respondRedirect("/")
        }
        get("/about") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "about.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        get("/search") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "search.hbs", mapOf(
                        "book" to book(
                            "/assets/img/general/notfound.webp",
                            "Mario Rossi", "The Lord of the Rings", "Come il governo italiano (distrutto)"
                        ), "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        get("/terms") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "terms.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        get("/who") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "who.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
        get("/policy") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                MustacheContent(
                    "policy.hbs", mapOf(
                        "user" to user(
                            name = UserData?.givenName,
                            surname = UserData?.familyName,
                            photo = UserData?.picture,
                            id = UserData?.sub,
                            email = UserData?.email,
                            realm = UserData?.hd,
                            gsuite = UserData?.GSuiteUser
                        ), "logged" to (call.sessions.get<UserData>() != null)
                    )
                )
            )
        }
    }
    log.info("[✓] Started Plugin - PublicFrontend.kt")
}