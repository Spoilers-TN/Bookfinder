package it.tn.spoilers.plugins.frontend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.data.user
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.ReviewsService


/**
 * Function containing the routing for the private (logged users) frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configurePrivateFrontend() {
    log.info("[!] Starting Plugin - PrivateFrontend.kt")
    routing {
        get("/profile") {
            val UserSession = call.sessions.get<UserSession>()
            val UserData = call.sessions.get<UsersData>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "profile.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                            "numReviews" to ReviewsService().findByRecipient(UserData.User_UUID).size,
                            "reviews" to ReviewsService().findByRecipient(UserData.User_UUID)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/settings") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "settings.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/dashboard") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "dashboard.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/insertion/new") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "newInsertion.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/messaggi") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "messages.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                uuid = UserData.User_UUID,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                email = UserData.User_Email,
                                bio = UserData.User_Biog,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/category") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/name") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/price") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/year") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/category") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }



    }
}