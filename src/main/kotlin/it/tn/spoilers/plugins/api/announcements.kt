package it.tn.spoilers.plugins.api


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.UserSession
import it.tn.spoilers.database.models.Announcements
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService
import it.tn.spoilers.plugins.database.toAnnouncementsData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Function containing the Books API -
 * **In development**
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureAnnouncementsApi() {
    log.info("[!] Starting Plugin - api - announcements.kt")
    val service = AnnouncementsService()
/*
    routing {
        get("/api/announcements/list") {
            val AnnouncementsList = service.findAll().map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/isbn/{isbn}") {
            val id = call.parameters["isbn"]!!.toLong()
            val AnnouncementsList = service.findByISBN(id).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/personal"){
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                val id = UserData.User_ID

                val AnnouncementsList = service.findAllByUser(id).map(Announcements::toAnnouncementsData)
                call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)

            } else {
                call.respond(HttpStatusCode.Unauthorized, "Amio, un pò difficile vedere i propri libri se non sei loggato o no ?")
            }
        }
        get("/api/announcements/user/{user}"){
            val userID = call.parameters["user"]!!.toString()
            val AnnouncementsList = service.findByUser(userID).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/category/{category}") {
            val category = call.parameters["category"]!!.toString()
            val AnnouncementsList = service.findByCategory(category).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/name/{name}") {
            val name = call.parameters["name"]!!.toString()
            val AnnouncementsList = service.findByName(name).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/price/{price}") {
            val price = call.parameters["price"]!!.toDouble()
            val AnnouncementsList = service.findByPrice(price).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/year/{year}") {
            val year = call.parameters["year"]!!.toInt()
            val AnnouncementsList = service.findByYear(year).map(Announcements::toAnnouncementsData)
            call.respondText(Json.encodeToString(AnnouncementsList), contentType = ContentType.Application.Json)
        }
    }
    */

    log.info("[✓] Started Plugin - api - announcements.kt")
}