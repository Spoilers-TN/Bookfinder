package it.tn.spoilers.plugins.backend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.data.user
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService

/**
 * Function containing the pages and routing for the announcement backend
 *
 * @author Cristoforetti e Dalri
 * @since Bookfinder - 2023.05.07
 */
fun Application.configureAnnouncementBackend() {
    log.info("[!] Starting Plugin - AnnouncementBackend.kt")
    routing {
        post("/insert/announcement") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                val formParameters = call.receiveParameters()
                var Ebook = false
                if (!formParameters["announcement-ebook"].isNullOrBlank()) {
                    Ebook = true
                }
                AnnouncementsService().assistedCreate(
                    formParameters["announcement-user"]?.toString() ?: "",
                    formParameters["announcement-isbn"]?.toLongOrNull() ?: 0L,
                    "disponibile",
                    formParameters["announcement-price"]?.toDoubleOrNull() ?: 0.0,
                    formParameters["announcement-stato"].toString(),
                    formParameters["announcement-description"]?.toString() ?: "",
                    Ebook
                )
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }

        post("/update/announcement") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                val formParameters = call.receiveParameters()
                var Ebook = false
                if (!formParameters["announcement-ebook"].isNullOrBlank()) {
                    Ebook = true
                }
                AnnouncementsService().modifyBySpecificID(
                    formParameters["id"]?.toString() ?: "",
                    formParameters["Price"]?.toDoubleOrNull() ?: 0.0,
                    formParameters["Stato"].toString(),
                    formParameters["Description"]?.toString() ?: "",
                    Ebook,
                )
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
    }
}