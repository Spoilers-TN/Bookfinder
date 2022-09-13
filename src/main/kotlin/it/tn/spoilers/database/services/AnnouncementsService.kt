package it.tn.spoilers.database.services


import it.tn.spoilers.database.models.Announcements
import org.litote.kmongo.*

class AnnouncementsService {
    private val client = KMongo.createClient("mongodb+srv://<username>:<password>@bookfinder-db.eukircn.mongodb.net/?retryWrites=true&w=majority")
    private val database = client.getDatabase("bookfinder")
    private val announcementsCollection = database.getCollection<Announcements>("Announcements")

    fun create(user: Announcements): Id<Announcements>?  {
        announcementsCollection.insertOne(user)
        return user.id
    }

    fun findAll(): List<Announcements> =
        announcementsCollection.find().toList()

    fun findByID(id: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_ID eq id
        return announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }

    fun findByISBN(isbn: Long): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_Book eq isbn
        return announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }

    fun findByUser(UserID: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_User eq UserID
        return announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }
}