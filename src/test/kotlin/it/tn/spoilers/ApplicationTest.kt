package it.tn.spoilers

import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import it.tn.spoilers.plugins.frontend.*
import it.tn.spoilers.plugins.serving.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Class for testing the application
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
class ApplicationTest {
    @Test
    /**
     * Test the application endpoints
     */
    fun testRoot() = testApplication {

    }
}