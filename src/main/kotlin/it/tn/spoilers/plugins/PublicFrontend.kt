package it.tn.spoilers.plugins

import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.server.mustache.Mustache
import io.ktor.server.mustache.MustacheContent
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*

fun Application.configurePublicFrontend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
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
                "Mario Rossi","The Lord of the Rings","Come il governo italiano (distrutto)"))))
        }
        get("/terms") {
            call.respond(MustacheContent("terms.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/who") {
            call.respond(MustacheContent("who.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/policy"){
            call.respond(MustacheContent("policy.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
    }
    log.info("[✓] Started Plugin - PublicFrontend.kt")
}


@Serializable
data class MustacheUser(val id: Int, val name: String)
@Serializable
data class book(val image: String, val seller: String, val title: String, val status: String )