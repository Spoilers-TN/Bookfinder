package it.tn.spoilers.plugins.extras

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Class for configuring the serialization
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}