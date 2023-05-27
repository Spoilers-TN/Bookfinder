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
 * Function containing the routing for the guests frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureHybridFrontend() {
    log.info("[!] Starting Plugin - HybridFrontend.kt")
    routing {
        get("/user/{User_UUID}") {
            val GuestID = call.parameters["User_UUID"]!!.toString()
            println(GuestID)
            val GuestUser = UsersService().ReturnUserByID(GuestID)
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "publicprofile.html", mapOf(
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
                        ),
                        "publicuser" to guestuser(
                            name = GuestUser.User_Name,
                            uuid = GuestUser.User_UUID,
                            surname = GuestUser.User_Surname,
                            photo = GuestUser.User_Photo,
                            bio = GuestUser.User_Biog
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
