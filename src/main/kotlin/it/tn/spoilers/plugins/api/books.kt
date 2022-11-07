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
fun Application.configureBooksApi() {
    log.info("[!] Starting Plugin - api - books.kt")
    val service = BooksService()

    routing {
        get("/api/books/list") {
            val BookList =
                service.findAll()
                    .map(Books::toBooksData)
            call.respondText(Json.encodeToString(BookList), contentType = ContentType.Application.Json)
        }
        get("/api/books/isbn/{isbn}") {
            val id = call.parameters["isbn"]!!.toLong()
            call.respondText(Json.encodeToString(service.findByISBN(id)), contentType = ContentType.Application.Json)
        }
    }
    log.info("[✓] Started Plugin - api - books.kt")
}