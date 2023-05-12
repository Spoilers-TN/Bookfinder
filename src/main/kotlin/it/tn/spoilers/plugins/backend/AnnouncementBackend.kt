package it.tn.spoilers.plugins.backend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.InsertionBook
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.data.user
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService
import it.tn.spoilers.database.services.BooksService

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
                if(BooksService().findBySpecificISBN(formParameters["announcement-isbn"]?.toLongOrNull() ?: 0L)!= null) {
                    AnnouncementsService().assistedCreate(
                        UserData.User_ID,
                        formParameters["announcement-isbn"]?.toLongOrNull() ?: 0L,
                        "Published",
                        formParameters["announcement-price"]?.toDoubleOrNull() ?: 0.0,
                        formParameters["announcement-stato"].toString(),
                        formParameters["announcement-description"]?.toString() ?: "",
                        Ebook
                    )
                }else{
                    call.respond(HttpStatusCode.BadRequest, "Insert ISBN code")
                }
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }

        post("/update/announcement") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            val service = AnnouncementsService()
            if (UserSession != null && UserData != null) {
                val formParameters = call.receiveParameters()
                if(service.findBySpecificID(formParameters["id"].toString())?.Announcement_User == UserData.User_ID) {
                    var Ebook = false
                    if (!formParameters["EBook"].isNullOrBlank()) {
                        Ebook = true
                    }
                    AnnouncementsService().modifyBySpecificID(
                        formParameters["id"].toString(),
                        formParameters["Price"]?.toDoubleOrNull() ?: 0.0,
                        formParameters["Stato"].toString(),
                        formParameters["Description"]?.toString() ?: "",
                        Ebook,
                    )
                }else{
                    call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
                }
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/delete/announcement/{id}") {
            val id = call.parameters["id"].toString()
            val service = AnnouncementsService()
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                if(service.findBySpecificID(id)?.Announcement_User == UserData.User_ID) {
                    service.deleteBySpecificId(id)
                }else{
                    call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
                }
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
    }
}