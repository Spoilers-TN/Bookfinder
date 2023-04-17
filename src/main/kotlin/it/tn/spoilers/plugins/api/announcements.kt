package it.tn.spoilers.plugins.api


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.services.BooksService
import it.tn.spoilers.plugins.database.toBooksData
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
    val service = BooksService()

    routing {
        get("/api/announcements/list") {
            val BookList =
                service.findAll()
                    .map(Books::toBooksData)
            call.respondText(Json.encodeToString(BookList), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/isbn/{isbn}") {
            val id = call.parameters["isbn"]!!.toLong()
            call.respondText(Json.encodeToString(service.findByISBN(id)), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/category/{category}") {
            val category = call.parameters["category"]!!.toString()
            call.respondText(Json.encodeToString(service.findByCategory(category)), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/name/{name}") {
            val name = call.parameters["name"]!!.toString()
            call.respondText(Json.encodeToString(service.findByName(name)), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/price/{price}") {
            val price = call.parameters["price"]!!.toDouble()
            call.respondText(Json.encodeToString(service.findByPrice(price)), contentType = ContentType.Application.Json)
        }
        get("/api/announcements/year/{year}") {
            val year = call.parameters["year"]!!.toInt()
            call.respondText(Json.encodeToString(service.findByYear(year)), contentType = ContentType.Application.Json)
        }
    }
    log.info("[✓] Started Plugin - api - announcements.kt")
}