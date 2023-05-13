package it.tn.spoilers.plugins.backend

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()

            if (userSession != null && userData != null) {
                val multipart = call.receiveMultipart()
                var ebook = false
                var imageFile: InputStream? = null
                val formData = mutableMapOf<String?, String?>()
                val annid = generateUUID()
                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            formData[part.name] = part.value
                            if (part.name == "announcement-ebook" && part.value.isNotBlank()) {
                                ebook = true
                            }
                        }
                        is PartData.FileItem -> {
                            if (part.name == "image") {
                                imageFile = part.streamProvider()
                            }
                        }
                        else -> {}
                    }
                }
                if (serviceBook.findBySpecificISBN(formData["announcement-isbn"]?.toLongOrNull() ?: 0) != null
                    && (formData["announcement-price"]?.isNotBlank() == true && formData["announcement-price"]?.toDoubleOrNull() != null)
                    && stati.contains(formData["announcement-stato"].toString())
                ) {
                    AnnouncementsService().assistedCreate(
                        userData.User_ID,
                        formData["announcement-isbn"]?.toLongOrNull() ?: 0L,
                        "Published",
                        formData["announcement-price"]?.toDoubleOrNull() ?: 0.0,
                        formData["announcement-stato"].toString(),
                        formData["announcement-description"].toString(),
                        ebook,
                        annid
                    )
                    imageFile?.let { serviceImage.create(annid, it) }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Insert ISBN code")
                }
                call.respondRedirect("/dashboard", permanent = false)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }

        }

        post("/update/announcement") {
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                val formParameters = call.receiveParameters()
                if(serviceAnnouncement.findBySpecificID(formParameters["id"].toString())?.Announcement_User == userData.User_ID) {
                    var ebook = false
                    if (!formParameters["EBook"].isNullOrBlank()) {
                        ebook = true
                    }
                    if((formParameters["Price"]?.toDoubleOrNull() ?: 0.0) > 0
                        && stati.contains(formParameters["Stato"].toString())
                    ) {
                        serviceAnnouncement.modifyBySpecificID(
                            formParameters["id"].toString(),
                            formParameters["Price"]?.toDoubleOrNull() ?: 0.0,
                            formParameters["Stato"].toString(),
                            formParameters["Description"].toString(),
                            ebook
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                if(serviceAnnouncement.findBySpecificID(id)?.Announcement_User == userData.User_ID) {
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