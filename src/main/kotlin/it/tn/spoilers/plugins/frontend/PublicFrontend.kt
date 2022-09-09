package it.tn.spoilers.plugins.frontend

import com.github.mustachejava.DefaultMustacheFactory
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserData
import it.tn.spoilers.data.book
import it.tn.spoilers.data.user

fun Application.configurePublicFrontend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
    install(Pebble) {
        loader(ClasspathLoader().apply {
            prefix = "templates"
        })
    }
    routing {
        get("/") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                PebbleContent(
                    "index.html", mapOf(
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
                PebbleContent(
                    "about.html", mapOf(
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
            with(call) {
                respond(
                    PebbleContent(
                            "search.html", mapOf(
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
                                ), "logged" to (sessions.get<UserData>() != null)
                            )
                        )
                    )
            }
        }
        get("/terms") {
            val UserData = call.sessions.get<UserData>()
            call.respond(
                PebbleContent(
                    "terms.html", mapOf(
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
                PebbleContent(
                    "who.html", mapOf(
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
                PebbleContent(
                    "policy.html", mapOf(
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