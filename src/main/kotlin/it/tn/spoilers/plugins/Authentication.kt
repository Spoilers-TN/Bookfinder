package it.tn.spoilers.plugins

import io.ktor.server.auth.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*
import java.time.Duration


fun Application.configureAuthentication() {
    log.info("[!] Starting Plugin - Authentication.kt")

    data class UserSession(val token: String)
    install(Sessions) {
        cookie<UserSession>("user_session")
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
    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/google-callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://oauth2.googleapis.com/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "750408780393-hn4fhicn68f04o3f7d8o2ng097q2pgo6.apps.googleusercontent.com",
                    clientSecret = "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",
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                )
            }
            client = HttpClient(Apache)
        }
        oauth("auth-oauth-github") {
            urlProvider = { "http://localhost:8080/github-callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "github",
                    authorizeUrl = "https://github.com/login/oauth/authorize",
                    accessTokenUrl = "https://github.com/login/oauth/access_token",
                    requestMethod = HttpMethod.Post,
                    clientId = "2b14dd96e49e46f5de2b",
                    clientSecret = "30b34c84a178729cc87d3985279c4b7be56266d3",
                    defaultScopes = listOf("https://api.github.com/user"),
                )
            }
            client = HttpClient(Apache)
        }
    }
    routing {
        authenticate("auth-oauth-google") {
            get("google-login") {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/google-callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString()))
                call.respondRedirect("/google-hello")
            }
            get("/google-hello") {
                val userSession: UserSession? = call.sessions.get()
                if (userSession != null) {
                    val userInfo: UserInfo = HttpClient(Apache).get("https://www.googleapis.com/oauth2/v2/userinfo") {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
                        }
                    }.body()
                    call.respondText("Hello, ${userInfo.name}!")
                } else {
                    call.respondText("Not authenticated")
                }
            }
        }
        authenticate("auth-oauth-github") {
            get("github-login") {
                // Redirects to 'authorizeUrl' automatically
            }

            get("/github-callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString()))
                call.respondRedirect("/github-hello")
            }
            get("/github-hello") {
                val userSession: UserSession? = call.sessions.get()
                if (userSession != null) {
                    val userInfo: UserInfo = HttpClient(Apache).get("https://api.github.com/user") {
                        headers {
                            append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
                        }
                    }.body()
                    call.respondText("Hello, ${userInfo.name}!")
                }
            }
        }
        authenticate("auth-oauth-google") {

        }
    }
    log.info("[✓] Started Plugin - Authentication.kt")

}
