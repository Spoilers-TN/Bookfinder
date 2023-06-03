package it.tn.spoilers.plugins.frontend

import io.pebbletemplates.pebble.loader.ClasspathLoader
import io.ktor.server.application.*
import io.ktor.server.pebble.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import it.tn.spoilers.data.Announcement
import it.tn.spoilers.data.AnnouncementExtended
import it.tn.spoilers.data.InsertionBook
import it.tn.spoilers.data.user
import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.database.services.AnnouncementsService
import it.tn.spoilers.database.services.BooksService
import it.tn.spoilers.database.services.ImageService
import it.tn.spoilers.database.services.UsersService

/**
 * Function containing the routing for the public frontend
 *
 * @author Francesco Masala
 * @since Bookfinder - 2022.8.18
 */
fun Application.configurePublicFrontend() {
    log.info("[!] Starting Plugin - PublicFrontend.kt")
    install(Pebble) {
        loader(ClasspathLoader().apply {
            prefix = "templates"
        })
    }
    routing {
        get("/") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "index.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/index") {
            call.respondRedirect("/")
        }
        get("/about") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "about.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        route("search") {
            get {
                val UserData = call.sessions.get<UsersData>()
                call.respond(
                    PebbleContent(
                        "search.html", mapOf(
                            "user" to user(
                                name = UserData?.User_Name,
                                surname = UserData?.User_Surname,
                                photo = UserData?.User_Photo,
                                id = UserData?.User_ID,
                                uuid = UserData?.User_UUID,
                                email = UserData?.User_Email,
                                realm = UserData?.User_School_Domain,
                                gsuite = UserData?.User_GSuite,
                                bio = UserData?.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null)
                        )
                    )
                )
            }
            post {
                val UserData = call.sessions.get<UsersData>() //Dati della sessione

                //Ottieni parametri del form
                val postParam = call.receiveParameters()
                val search = postParam.get("search-query").toString()

                //?
                val announcements = AnnouncementsService().findByName(search)


                val annList = mutableListOf<Announcement>()
                val bookList = mutableListOf<InsertionBook>()
                val imageList = mutableListOf<String>()
                val namesList = mutableListOf<String>()


                for (ann in announcements) {
                    val book = BooksService().findBySpecificISBN(ann.Announcement_Book)
                    annList.add(
                        Announcement(
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
                        )
                    )
                    val user = UsersService().findByID


                    bookList.add(
                        InsertionBook(
                            author = book?.Book_Author,
                            name = book?.Book_Title,
                            isbn = book?.Book_ISBN,
                            category = book?.Book_Category,
                            publishers = book?.Book_Publishers
                        )
                    )
                    var immagine = ImageService().getByFileName(ann.Announcement_ID)

                    imageList.add(ImageService().encodeImageToBase64(immagine))
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

                call.respond( //Invia la pagina all'utente
                    PebbleContent(
                        "search-results.html", mapOf(
                            "user" to user(
                                name = UserData?.User_Name,
                                surname = UserData?.User_Surname,
                                photo = UserData?.User_Photo,
                                id = UserData?.User_ID,
                                uuid = UserData?.User_UUID,
                                email = UserData?.User_Email,
                                realm = UserData?.User_School_Domain,
                                gsuite = UserData?.User_GSuite,
                                bio = UserData?.User_Biog
                            ), "logged" to (call.sessions.get<UsersData>() != null),
                            "search" to search,
                            "ann" to announcementslist
                        )
                    )
                )
            }

            /*
        get("/search") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "search.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/search/results") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "risultati-ricerca.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
         */
        }


        get("/terms") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "terms.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/who") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "who.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
        get("/policy") {
            val UserData = call.sessions.get<UsersData>()
            call.respond(
                PebbleContent(
                    "policy.html", mapOf(
                        "user" to user(
                            name = UserData?.User_Name,
                            surname = UserData?.User_Surname,
                            photo = UserData?.User_Photo,
                            id = UserData?.User_ID,
                            uuid = UserData?.User_UUID,
                            email = UserData?.User_Email,
                            realm = UserData?.User_School_Domain,
                            gsuite = UserData?.User_GSuite,
                            bio = UserData?.User_Biog
                        ), "logged" to (call.sessions.get<UsersData>() != null)
                    )
                )
            )
        }
    }
    log.info("[✓] Started Plugin - PublicFrontend.kt")
}
