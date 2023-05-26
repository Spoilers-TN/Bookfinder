package it.tn.spoilers.plugins.frontend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.*
import it.tn.spoilers.database.models.AnnouncementsData
import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService
import it.tn.spoilers.database.services.BooksService
import it.tn.spoilers.database.services.ReviewsService


/**
 * Function containing the routing for the private (logged users) frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configurePrivateFrontend() {
    log.info("[!] Starting Plugin - PrivateFrontend.kt")
    routing {
        get("/profile") {
            val UserSession = call.sessions.get<UserSession>()
            val UserData = call.sessions.get<UsersData>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "profile.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                            "numReviews" to ReviewsService().findByRecipient(UserData.User_UUID).size,
                            "reviews" to ReviewsService().findByRecipient(UserData.User_UUID)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/profile") {
            val UserSession = call.sessions.get<UserSession>()
            val UserData = call.sessions.get<UsersData>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "profile.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                            "numReviews" to ReviewsService().findByRecipient(UserData.User_UUID).size,
                            "reviews" to ReviewsService().findByRecipient(UserData.User_UUID)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/insertion/manage") {
            val userSession = call.sessions.get<UserSession>()
            val userData = call.sessions.get<UsersData>()
            if (userSession != null && userData != null) {
                val announcements = AnnouncementsService().findAllByUser(userData.User_ID)
                val annList = mutableListOf<Announcement>()
                val bookList = mutableListOf<InsertionBook>()
                for (ann in announcements) {
                    val book = BooksService().findBySpecificISBN(ann.Announcement_Book)
                    annList.add(Announcement(
                        ID = ann.Announcement_ID,
                        User = ann.Announcement_User,
                        Book = ann.Announcement_Book,
                        Publish_Date = ann.Announcement_Publish_Date,
                        Expire_Date = ann.Announcement_Expire_Date,
                        Status = ann.Announcement_Status,
                        Price = ann.Announcement_Price,
                        Book_Status = ann.Announcement_Book_Status,
                        Description = ann.Announcement_Description,
                        Ebook = ann.Announcement_Ebook,
                    ))
                    bookList.add(InsertionBook(
                        author = book?.Book_Author,
                        name = book?.Book_Title,
                        isbn = book?.Book_ISBN,
                        category = book?.Book_Category,
                        publishers = book?.Book_Publishers
                    ))
                }
                val annBookPairs = annList.zip(bookList)
                val announcementslist = mutableListOf<AnnouncementExtended>()
                for ((ann, book) in annBookPairs) {
                    announcementslist.add(
                            AnnouncementExtended(
                                ID = ann.ID,
                                User = ann.User,
                                Book = ann.Book,
                                Publish_Date = ann.Publish_Date,
                                Expire_Date = ann.Expire_Date,
                                Status = ann.Status,
                                Price = ann.Price,
                                Book_Status = ann.Book_Status,
                                Description = ann.Description,
                                Ebook = ann.Ebook,
                                Author = book.author,
                                Name = book.name,
                                ISBN = book.isbn,
                                Category = book.category,
                                Publishers = book.publishers
                            )
                    )
                }
                call.respond(PebbleContent("lista-annunci-pubblicati.html", mapOf(
                    "user" to user(
                        name = userData.User_Name,
                        surname = userData.User_Surname,
                        photo = userData.User_Photo,
                        id = userData.User_ID,
                        uuid = userData.User_UUID,
                        email = userData.User_Email,
                        realm = userData.User_School_Domain,
                        gsuite = userData.User_GSuite,
                        bio = userData.User_Biog
                    ),"logged" to (call.sessions.get<UsersData>() != null),
                    "ann" to announcementslist
                )))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/settings") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "settings.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/dashboard") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "dashboard.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/insertion/new") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "newInsertion.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }

        get("/messaggi") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "messages.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                uuid = UserData.User_UUID,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                email = UserData.User_Email,
                                bio = UserData.User_Biog,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/category") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                //call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/name") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                //call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/price") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                //call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/year") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                //call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/category") {
            val UserData = call.sessions.get<UsersData>()
            val UserSession = call.sessions.get<UserSession>()
            if (UserSession != null && UserData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData.User_Name,
                                surname = UserData.User_Surname,
                                photo = UserData.User_Photo,
                                id = UserData.User_ID,
                                uuid = UserData.User_UUID,
                                email = UserData.User_Email,
                                realm = UserData.User_School_Domain,
                                gsuite = UserData.User_GSuite,
                                bio = UserData.User_Biog
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                        )
                    )
                )
            } else {
                //call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }



    }
}