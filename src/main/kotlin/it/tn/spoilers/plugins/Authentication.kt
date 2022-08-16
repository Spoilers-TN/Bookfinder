package it.tn.spoilers.plugins

import io.ktor.server.auth.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*
import java.time.Duration
import kotlinx.serialization.json.*

fun Application.configureAuthentication() {
    log.info("[!] Starting Plugin - Authentication.kt")

    data class UserSession(val token: String)
    data class UserData(val id: String,
                        val name: String,
                        @SerialName("given_name") val givenName: String,
                        @SerialName("family_name") val familyName: String,
                        val picture: String,
                        val locale: String)
    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
        }
        cookie<UserData>("user_data") {
            cookie.path = "/profile"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
        }
    }
    install (Authentication) {
        oauth("auth-oauth-google") {
            client = HttpClient(Apache)
            urlProvider = { "https://bookfinder.spoilers.tn.it/google-callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "750408780393-hn4fhicn68f04o3f7d8o2ng097q2pgo6.apps.googleusercontent.com",
                    clientSecret = "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",
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                )
            }

        }
    }
    routing {
        authenticate("auth-oauth-google") {
            get("google-login") {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/google-callback") {
                val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                    ?: error("No principal")
                val json = HttpClient(Apache).get("https://www.googleapis.com/userinfo/v2/me") {
                    header("Authorization", "Bearer ${principal.accessToken}")
                }.bodyAsText()
                val UserDataFromJson = Json.decodeFromString<UserInfo>(json)
                call.sessions.set(UserSession(principal.accessToken))
                println(UserDataFromJson.toString())
                call.sessions.set(UserData(
                    UserDataFromJson.id,
                    UserDataFromJson.name,
                    UserDataFromJson.givenName,
                    UserDataFromJson.familyName,
                    UserDataFromJson.picture,
                    UserDataFromJson.locale
                ))
                call.respondRedirect("/profile")
            }
        }
        get("/profile") {
            val UserSession = call.sessions.get<UserSession>()
            val UserData = call.sessions.get<UserData>()
            if(UserSession != null) {
                println(UserData?.familyName)
                call.respondText("Benvenuto ${UserData?.name}, il tuo ID è ${UserData?.id} ")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }

        }
    }
    log.info("[✓] Started Plugin - Authentication.kt")

}
@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)