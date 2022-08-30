package it.tn.spoilers.plugins.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.services.BooksService
import it.tn.spoilers.plugins.database.toBooksData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.configureBooksApi() {
    log.info("[!] Starting Plugin - api - books.kt")
    val service = BooksService()

    routing {
        get("/api/books/list") {
                val BookList =
                    service.findAll()
                        .map(Books::toBooksData)
                call.respond(Json.encodeToString(BookList))
        }
    }
    log.info("[✓] Started Plugin - api - books.kt")
}