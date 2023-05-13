package it.tn.spoilers.plugins.backend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
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
    val serviceAnnouncement = AnnouncementsService()
    val serviceBook = BooksService()
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
                val stati = arrayOf("Pessimo stato", "Cattivo stato", "Discreto stato", "Buono stato", "Ottimo stato")
                if(serviceBook.findBySpecificISBN(formParameters["announcement-isbn"]?.toLongOrNull() ?: 0) != null
                    && (formParameters["announcement-price"]?.toDoubleOrNull() ?: 0.0) > 0
                    && stati.contains(formParameters["announcement-stato"].toString())
                ) {
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
                    call.respond(HttpStatusCode.Unauthorized, "Insert ISBN code")
                }
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
                if(serviceAnnouncement.findBySpecificID(formParameters["id"].toString())?.Announcement_User == UserData.User_ID) {
                    var Ebook = false
                    if (!formParameters["EBook"].isNullOrBlank()) {
                        Ebook = true
                    }
                    val stati = arrayOf("Pessimo stato", "Cattivo stato", "Discreto stato", "Buono stato", "Ottimo stato")
                    if((formParameters["Price"]?.toDoubleOrNull() ?: 0.0) > 0
                        && stati.contains(formParameters["Stato"].toString())
                    ) {
                        serviceAnnouncement.modifyBySpecificID(
                            formParameters["id"].toString(),
                            formParameters["Price"]?.toDoubleOrNull() ?: 0.0,
                            formParameters["Stato"].toString(),
                            formParameters["Description"]?.toString() ?: "",
                            Ebook,
                        )
                    }
                }else{
                    call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
                }
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        post("/insertion/redirect") {
            var isbn = call.receiveParameters()["input-isbn"]
            if (isbn == null) {
                isbn = 0.toString()
            }
            call.respondRedirect("/insertion/new/$isbn", permanent = false)
        }
        get("/delete/announcement/{id}") {
            val id = call.parameters["id"].toString()
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                if(serviceAnnouncement.findBySpecificID(id)?.Announcement_User == UserData.User_ID) {
                    serviceAnnouncement.deleteBySpecificId(id)
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