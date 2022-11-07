package it.tn.spoilers

import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import it.tn.spoilers.plugins.frontend.*
import it.tn.spoilers.plugins.serving.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
            configureStaticRoutes()
            configurePublicFrontend()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/about").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/search").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/terms").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}