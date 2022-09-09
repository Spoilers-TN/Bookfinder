package it.tn.spoilers.plugins.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.tn.spoilers.database.services.ReviewsService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.configureReviewsApi() {
    log.info("[!] Starting Plugin - api - schools.kt")
    val service = ReviewsService()

    routing {
        get("/api/reviews/{UserID}/recived") {
            val UserID = call.parameters["UserID"].toString()
            call.respondText(Json.encodeToString(service.findByRecipient(UserID)), contentType = ContentType.Application.Json)
        }
        get("/api/reviews/{UserID}/sended") {
            val UserID = call.parameters["UserID"].toString()
            call.respondText(Json.encodeToString(service.findBySender(UserID)), contentType = ContentType.Application.Json)
        }

    }
    log.info("[✓] Started Plugin - api - schools.kt")
}