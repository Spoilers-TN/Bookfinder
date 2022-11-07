package it.tn.spoilers.plugins.frontend

import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.guestuser
import it.tn.spoilers.data.user
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.ReviewsService
import it.tn.spoilers.database.services.UsersService

/**
 * Function containing the routing for the admin frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureAdminFrontend() {
    log.info("[!] Starting Plugin - HybridFrontend.kt")
    routing {
        get("/admin") {
            val GuestID = call.parameters["User_UUID"]!!.toString()
            println(GuestID)
            val GuestUser = UsersService().ReturnUserByUUID(GuestID)
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "publicprofile.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            uuid = UserData?.User_UUID,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite
                        ),
                        "publicuser" to guestuser(
                            name = GuestUser.User_Name,
                            uuid = GuestUser.User_UUID,
                            surname = GuestUser.User_Surname,
                            photo = GuestUser.User_Photo,
                        ),
                        "logged" to (call.sessions.get<UsersData>() != null),
                        "numReviews" to ReviewsService().findByRecipient(GuestUser.User_UUID).size,
                        "reviews" to ReviewsService().findByRecipient(GuestUser.User_UUID)
                    )
                )
            )
        }
        get("/book/{Book_ISBN}") {
            val GuestID = call.parameters["User_UUID"]!!.toString()
            println(GuestID)
            val GuestUser = UsersService().ReturnUserByUUID(GuestID)
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "publicprofile.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            uuid = UserData?.User_UUID,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite
                        ),
                        "publicuser" to guestuser(
                            name = GuestUser.User_Name,
                            uuid = GuestUser.User_UUID,
                            surname = GuestUser.User_Surname,
                            photo = GuestUser.User_Photo,
                        ),
                        "logged" to (call.sessions.get<UsersData>() != null),
                        "numReviews" to ReviewsService().findByRecipient(GuestUser.User_UUID).size,
                        "reviews" to ReviewsService().findByRecipient(GuestUser.User_UUID)
                    )
                )
            )
        }
        get("/announcement/{Announcement_ID}") {
            val GuestID = call.parameters["User_UUID"]!!.toString()
            println(GuestID)
            val GuestUser = UsersService().ReturnUserByUUID(GuestID)
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "publicprofile.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            uuid = UserData?.User_UUID,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite
                        ),
                        "publicuser" to guestuser(
                            name = GuestUser.User_Name,
                            uuid = GuestUser.User_UUID,
                            surname = GuestUser.User_Surname,
                            photo = GuestUser.User_Photo,
                        ),
                        "logged" to (call.sessions.get<UsersData>() != null),
                        "numReviews" to ReviewsService().findByRecipient(GuestUser.User_UUID).size,
                        "reviews" to ReviewsService().findByRecipient(GuestUser.User_UUID)
                    )
                )
            )
        }
    }
    log.info("[✓] Started Plugin - HybridFrontend.kt")
}