package it.tn.spoilers.plugins.backend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.UsersService

/**
 * Function containing the pages and routing for the user backend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.11.11
 */
fun Application.configureUserBackend() {
    log.info("[!] Starting Plugin - UserBackend.kt")
    routing {
        post("/core/v1/users/profile") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                val formParameters = call.receiveParameters()
                val username = formParameters["bio"].toString()
                UsersService().updateUserBio(UserData.User_UUID, username)
                call.sessions.set(UsersService().ReturnUserByUUID(UserData.User_UUID))
                call.respondRedirect("/settings", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/core/v1/users/profile") {
            call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
        }
    }
}