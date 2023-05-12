package it.tn.spoilers.plugins.security

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import it.tn.spoilers.data.*
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.UsersService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.Duration
import java.util.*
import kotlin.system.exitProcess

/**
 * Function containing the OAuth2 Security - Cookies Security and Login/Logout
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configureAuthentication() {
    log.info("[!] Starting Plugin - OAuthSecurity.kt")
    var service = UsersService()

    //Controlla se application.properties esiste
    val prop = Properties()
    try {
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
    } catch (ex: Exception){
        log.info("[!] The application.properties file is missing.")
        log.info(ex.message);
        exitProcess(-1);
    }

    install(Sessions) {
        val secretSignKey = hex(prop.getProperty("my.signKey"))
        val secretEncryptKey = hex(prop.getProperty("my.encryptKey"))
        cookie<UserSession>("user_session") {
            cookie.secure = prop.getProperty("my.cookieSecure").toBoolean()
            cookie.domain = prop.getProperty("my.domain")
            cookie.path = "/"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
        cookie<UsersData>("user_data") {
            cookie.secure = prop.getProperty("my.cookieSecure").toBoolean()
            cookie.domain = prop.getProperty("my.domain")
            cookie.path = "/"
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }
    install(Authentication) {
        oauth("auth-oauth-google") {
            client = HttpClient(Apache)
            urlProvider = { prop.getProperty("my.urlProvider") }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "750408780393-hn4fhicn68f04o3f7d8o2ng097q2pgo6.apps.googleusercontent.com",
                    clientSecret = "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",
                    defaultScopes = listOf(
                        "https://www.googleapis.com/auth/userinfo.profile",
                        "https://www.googleapis.com/auth/userinfo.email"
                    )
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
                if (json.contains("hd")) {
                    val UserDataFromJson = Json { ignoreUnknownKeys = true }.decodeFromString<UserInfoGSuite>(json)
                    service.createIfNotPresent(CastGsuiteUserToUserDb(UserDataFromJson))
                    call.sessions.set(UserSession(principal.accessToken))
                    call.sessions.set(
                        service.ReturnUserByID(UserDataFromJson.sub)
                    )

                } else {
                    val UserDataFromJson = Json { ignoreUnknownKeys = true; }.decodeFromString<UserInfo>(json)
                    service.createIfNotPresent(CastNormalUserToUserDb(UserDataFromJson))
                    call.sessions.set(UserSession(principal.accessToken))
                    call.sessions.set(
                        service.ReturnUserByID(UserDataFromJson.sub)
                    )
                }
                call.respondRedirect("/dashboard")
            }
        }
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.sessions.clear<UsersData>()
            call.respond(
                PebbleContent(
                    "logout.html",
                    mapOf("logged" to (call.sessions.get<UsersData>() != null))
                )
            )
        }
        get("/logoff") {
            call.sessions.clear<UserSession>()
            call.sessions.clear<UsersData>()
            call.respond(
                PebbleContent(
                    "logout.html",
                    mapOf("logged" to (call.sessions.get<UsersData>() != null))
                )
            )
        }
    }
    log.info("[✓] Started Plugin - OAuthSecurity.kt")

}

