package it.tn.spoilers.plugins

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.mustache.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.Duration

fun Application.configureAuthentication() {
    log.info("[!] Starting Plugin - Authentication.kt")

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.secure = true
            cookie.domain = "bookfinder.spoilers.tn.it"
            cookie.encoding = CookieEncoding.BASE64_ENCODING
            cookie.path = "/"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
        }
        cookie<UserData>("user_data") {
            cookie.secure = true
            cookie.domain = "bookfinder.spoilers.tn.it"
            cookie.encoding = CookieEncoding.BASE64_ENCODING
            cookie.path = "/"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
        }
    }
    install(Authentication) {
        oauth("auth-oauth-google") {
            client = HttpClient(Apache)
            urlProvider = { "https://bookfinder.spoilers.tn.it/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "750408780393-hn4fhicn68f04o3f7d8o2ng097q2pgo6.apps.googleusercontent.com",
                    clientSecret = "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",
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email")
                )
            }

        }
    }
    routing {
        authenticate("auth-oauth-google") {
            get("/login") {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/callback") {
                val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                    ?: error("No principal")
                val json = HttpClient(Apache).get("https://www.googleapis.com/oauth2/v3/userinfo") {
                    header("Authorization", "Bearer ${principal.accessToken}")
                }.bodyAsText()
                if (json.contains("hd")){
                        val UserDataFromJson = Json.decodeFromString<UserInfoGSuite>(json)
                        call.sessions.set(UserSession(principal.accessToken))
                        call.sessions.set(UserData(
                            UserDataFromJson.sub,
                            UserDataFromJson.name,
                            UserDataFromJson.givenName,
                            UserDataFromJson.familyName,
                            UserDataFromJson.picture,
                            UserDataFromJson.email,
                            UserDataFromJson.email_verified,
                            UserDataFromJson.locale,
                            UserDataFromJson.hd
                        ))

                } else {
                        val UserDataFromJson = Json.decodeFromString<UserInfo>(json)
                        call.sessions.set(UserSession(principal.accessToken))
                        call.sessions.set(
                            UserData(
                                UserDataFromJson.sub,
                                UserDataFromJson.name,
                                UserDataFromJson.givenName,
                                UserDataFromJson.familyName,
                                UserDataFromJson.picture,
                                UserDataFromJson.email,
                                UserDataFromJson.email_verified,
                                UserDataFromJson.locale,
                                "gmail.com"
                            )
                        )
                }
                call.respondRedirect("/dashboard")
            }
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.sessions.clear<UserData>()
            call.respondRedirect("/")
        }
    }
    log.info("[✓] Started Plugin - Authentication.kt")

}