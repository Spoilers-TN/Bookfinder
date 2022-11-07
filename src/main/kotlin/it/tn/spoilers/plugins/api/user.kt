package it.tn.spoilers.plugins.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.UsersService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Function containing the Users API -
 * **In development**
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureUsersApi() {
    log.info("[!] Starting Plugin - api - users.kt")
    val service = UsersService()

    routing {
        get("/api/user/info") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respondText(
                    Json.encodeToString(service.findByGoogleID(UserData.User_ID)),
                    contentType = ContentType.Application.Json
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }

        }

        post("/api/user/set/bio") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                val param = call.receive<Parameters>()
                val biography = param["biography"]!!
                println(biography)
                println(param.toString())
                service.updateUserBio(UserData.User_ID, biography)
                call.respond(HttpStatusCode(200, "OK"))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
    }
    log.info("[✓] Started Plugin - api - users.kt")
}