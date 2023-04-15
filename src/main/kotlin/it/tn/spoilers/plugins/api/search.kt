package it.tn.spoilers.plugins.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.tn.spoilers.database.models.School
import it.tn.spoilers.database.services.SchoolService
import it.tn.spoilers.plugins.database.toSchoolData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Function containing the Search API -
 * @author Kevin/Berti/Lunelli
 */
fun Application.configureSearchApi() {
    log.info("[!] Starting Plugin - api - search.kt")
    val service = SearchService()

    routing {
        get("/api/search/category/{category}") {
            val BookList =
                service.findAll()
                    .map(Search)



            val SchoolList =
                service.findAll()
                    .map(School::toSchoolData)
            call.respondText(Json.encodeToString(SchoolList), contentType = ContentType.Application.Json)
        }
        get("/api/search/name/{name}") {
            val SchoolList =
                service.findAll()
                    .map(School::toSchoolData)
            call.respondText(Json.encodeToString(SchoolList), contentType = ContentType.Application.Json)
        }
        get("/api/search/price/{price}") {
            val SchoolList =
                service.findAll()
                    .map(School::toSchoolData)
            call.respondText(Json.encodeToString(SchoolList), contentType = ContentType.Application.Json)
        }
        get("/api/search/year/{year}") {
            val SchoolList =
                service.findAll()
                    .map(School::toSchoolData)
            call.respondText(Json.encodeToString(SchoolList), contentType = ContentType.Application.Json)
        }
    }
    log.info("[✓] Started Plugin - api - search.kt")
}