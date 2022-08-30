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

fun Application.configureSchoolsApi() {
    log.info("[!] Starting Plugin - api - schools.kt")
    val service = SchoolService()

    routing {
        get("/api/schools/list") {
            val SchoolList =
                service.findAll()
                    .map(School::toSchoolData)
            call.respondText(Json.encodeToString(SchoolList), contentType = ContentType.Application.Json)
        }
        get("/api/schools/code/{code}") {
            val id = call.parameters["code"].toString()
            call.respondText(Json.encodeToString(service.findByCode(id)), contentType = ContentType.Application.Json)
        }
    }
    log.info("[✓] Started Plugin - api - schools.kt")
}