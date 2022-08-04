package it.tn.spoilers.plugins

import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.server.mustache.Mustache
import io.ktor.server.mustache.MustacheContent
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureFrontend() {
    log.info("[!] Starting Plugin - Frontend.kt")
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    routing {
        get("/") {
            call.respond(MustacheContent("index.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/index") {
            call.respond(MustacheContent("index.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/about") {
            call.respond(MustacheContent("about.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/search") {
            call.respond(MustacheContent("search.hbs", mapOf("book" to book("/assets/img/general/notfound.webp",
                "Nicola Leoni","The Lord of the Rings","Quasi Decente"))))
        }
        get("/terms") {
            call.respond(MustacheContent("terms.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
    }
    log.info("[✓] Started Plugin - Frontend.kt")
}

data class MustacheUser(val id: Int, val name: String)
data class book(val image: String, val seller: String, val title: String, val status: String )