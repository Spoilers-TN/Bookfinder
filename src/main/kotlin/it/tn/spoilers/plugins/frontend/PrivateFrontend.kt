package it.tn.spoilers.plugins.frontend

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.*
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService
import it.tn.spoilers.database.services.BooksService
import it.tn.spoilers.database.services.ImageService
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
            val userSession = call.sessions.get<UserSession>()
            val userData = call.sessions.get<UsersData>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "profile.html", mapOf(
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
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                            "numReviews" to ReviewsService().findByRecipient(userData.User_UUID).size,
                            "reviews" to ReviewsService().findByRecipient(userData.User_UUID)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/profile") {
            val userSession = call.sessions.get<UserSession>()
            val userData = call.sessions.get<UsersData>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "profile.html", mapOf(
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
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null),
                            "numReviews" to ReviewsService().findByRecipient(userData.User_UUID).size,
                            "reviews" to ReviewsService().findByRecipient(userData.User_UUID)
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
                val imageList = mutableListOf<String>()
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
                    imageList.add(ImageService().encodeImageToBase64(ImageService().getByFileName(ann.Announcement_ID)))
                }
                val annBookPairs = zip3(annList,bookList,imageList) {a, b, c -> Triple(a,b,c)}
                val announcementslist = mutableListOf<AnnouncementExtended>()
                for ((ann, book, image) in annBookPairs) {
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
                                Publishers = book.publishers,
                                base64Image = image
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "settings.html", mapOf(
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
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/dashboard") {
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "dashboard.html", mapOf(
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
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/insertion/new/{isbn}") {
            val id = call.parameters["isbn"]!!.toLong()
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            val book = BooksService().findBySpecificISBN(id)
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "newInsertion.html", mapOf(
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
                            ),
                            "book" to InsertionBook(
                                author = book?.Book_Author,
                                name = book?.Book_Title,
                                isbn = book?.Book_ISBN,
                                category = book?.Book_Category,
                                publishers = book?.Book_Publishers
                            ),
                            "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/insertion/modify/{id}") {
            val id = call.parameters["id"]!!.toString()
            val serviceImage = ImageService()
            val imageStream = serviceImage.getByFileName(id)
            val base64Image = serviceImage.encodeImageToBase64(imageStream)
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            val ann = AnnouncementsService().findBySpecificID(id)
            val book = BooksService().findBySpecificISBN(ann!!.Announcement_Book)
            if (userSession != null && userData != null) {
                if(ann.Announcement_User == userData.User_ID) {
                    call.respond(
                        PebbleContent(
                            "modify-announcement.html", mapOf(
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
                                ),
                                "ann" to Announcement(
                                    ID = ann.Announcement_ID,
                                    User = ann.Announcement_User,
                                    Book = ann.Announcement_Book,
                                    Publish_Date = ann.Announcement_Publish_Date,
                                    Expire_Date = ann.Announcement_Expire_Date,
                                    Status = ann.Announcement_Status,
                                    Price = ann.Announcement_Price,
                                    Book_Status = ann.Announcement_Book_Status,
                                    Description = ann.Announcement_Description,
                                    Ebook = ann.Announcement_Ebook
                                ),"book" to  InsertionBook(
                                    author = book?.Book_Author,
                                    name = book?.Book_Title,
                                    isbn = book?.Book_ISBN,
                                    category = book?.Book_Category,
                                    publishers = book?.Book_Publishers
                                ),
                                "logged" to (call.sessions.get<UsersData>() != null),
                                "base64Image" to base64Image
                            )
                        )
                    )
                }else {
                    call.respond(HttpStatusCode.Unauthorized, "Not yours")
                }
            } else {
                call.respond(HttpStatusCode.NotFound, "Image not found")
            }
        }
        get("/messaggi") {
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "messages.html", mapOf(
                            "user" to user(
                                name = userData.User_Name,
                                uuid = userData.User_UUID,
                                surname = userData.User_Surname,
                                photo = userData.User_Photo,
                                id = userData.User_ID,
                                email = userData.User_Email,
                                bio = userData.User_Biog,
                                realm = userData.User_School_Domain,
                                gsuite = userData.User_GSuite
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Not authenticated")
            }
        }
        get("/search/category") {
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
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
            val userData = call.sessions.get<UsersData>()
            val userSession = call.sessions.get<UserSession>()
            if (userSession != null && userData != null) {
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
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

fun <A, B, C, R> zip3(listA: List<A>, listB: List<B>, listC: List<C>, transform: (A, B, C) -> R): List<R> {
    val minSize = minOf(listA.size, listB.size, listC.size)
    val result = ArrayList<R>(minSize)

    for (i in 0 until minSize) {
        result.add(transform(listA[i], listB[i], listC[i]))
    }

    return result
}
