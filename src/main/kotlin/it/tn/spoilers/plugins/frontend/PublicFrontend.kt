package it.tn.spoilers.plugins.frontend

import com.mitchellbosecke.pebble.loader.ClasspathLoader
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.user
import it.tn.spoilers.database.services.*
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.BooksService

/**
 * Function containing the routing for the public frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configurePublicFrontend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
    install(Pebble) {
        loader(ClasspathLoader().apply {
            prefix = "templates"
        })
    }
    routing {
        get("/") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "index.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/index") {
            call.respondRedirect("/")
        }
        get("/about") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "about.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/terms") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "terms.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/who") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "who.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/policy") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "policy.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }

        route("search") {
            get {
                val UserData = call.sessions.get<UsersData>()
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData?.User_Name,
                                surname = UserData?.User_Surname,
                                photo = UserData?.User_Photo,
                                id = UserData?.User_ID,
                                uuid = UserData?.User_UUID,
                                email = UserData?.User_Email,
                                realm = UserData?.User_School_Domain,
                                gsuite = UserData?.User_GSuite,
                                bio = UserData?.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            }
            post {
                val UserData = call.sessions.get<UsersData>() //Dati della sessione

                //Ottieni parametri del form
                val postParam = call.receiveParameters()
                val search = postParam.get("search-query").toString()

                call.respond( //Invia la pagina all'utente
                    PebbleContent(
                        "search-results.html", mapOf(
                            "user" to user(
                                name = UserData?.User_Name,
                                surname = UserData?.User_Surname,
                                photo = UserData?.User_Photo,
                                id = UserData?.User_ID,
                                uuid = UserData?.User_UUID,
                                email = UserData?.User_Email,
                                realm = UserData?.User_School_Domain,
                                gsuite = UserData?.User_GSuite,
                                bio = UserData?.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null),
                            "search" to search,
                            "announcements" to AnnouncementsService().findByName(search)
                        )
                    )
                )
            }

            /*

        get("/search") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "search.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/search/results") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "risultati-ricerca.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }


         */
        }
    }
    log.info("[✓] Started Plugin - PublicFrontend.kt")
}