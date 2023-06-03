package it.tn.spoilers

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import it.tn.spoilers.extras.DisableLogging
import it.tn.spoilers.extras.DisableMongoLogging
import it.tn.spoilers.extras.EnableSentry
import it.tn.spoilers.plugins.api.*
import it.tn.spoilers.plugins.backend.configureAnnouncementBackend
import it.tn.spoilers.plugins.backend.configurePublicBackend
import it.tn.spoilers.plugins.backend.configureUserBackend
import it.tn.spoilers.plugins.extras.configureMonitoring
import it.tn.spoilers.plugins.extras.configureSockets
import it.tn.spoilers.plugins.frontend.configureHybridFrontend
import it.tn.spoilers.plugins.frontend.configurePrivateFrontend
import it.tn.spoilers.plugins.frontend.configurePublicFrontend
import it.tn.spoilers.plugins.http.configureErrors
import it.tn.spoilers.plugins.http.configureHeaders
import it.tn.spoilers.plugins.security.configureAuthentication
import it.tn.spoilers.plugins.security.configureCORS
import it.tn.spoilers.plugins.serving.configureCompression
import it.tn.spoilers.plugins.serving.configureRouting
import it.tn.spoilers.plugins.serving.configureStaticRoutes
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.litote.kmongo.json


/**
 * Function containing EVERYTHING
 *
 * @author Francesco Masala
 * @since Bookfinder - Initial Commit
 */
fun main() {
    DisableLogging()
    DisableMongoLogging()
    EnableSentry()
    println("[!] Starting Server - BookFinder - v2022.11.07-Alpha")
    System.setProperty("io.ktor.development", "true")
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::module, watchPaths = listOf("classes") ).start(wait = true)
    println("[✓] Started Server - BookFinder")
}

fun Application.module() {

    configureRouting()
    configureAuthentication()
    configureErrors()
    //configureSerialization()
    configureStaticRoutes()
    configureMonitoring()
    configureSockets()
    configureHeaders()
    configureCompression()
    configureCORS()

    //Configure APIs
    configureAnnouncementsApi()
    configureBooksApi()
    configureSchoolsApi()
    configureUsersApi()
    configureReviewsApi()

    //Configure backend
    configureAnnouncementBackend()
    configurePublicBackend()
    configureUserBackend()

    //Configure frontend
    configurePublicFrontend()
    configureHybridFrontend()
    configurePrivateFrontend()
}
