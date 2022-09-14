package it.tn.spoilers.database.services


import it.tn.spoilers.database.models.Announcements
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class AnnouncementsService {
    private val client = KMongo.createClient("mongodb+srv://<username>:<password>@bookfinder-db.eukircn.mongodb.net/?retryWrites=true&w=majority")
    private val database = client.getDatabase("bookfinder")
    private val announcementsCollection = database.getCollection<Announcements>("Announcements")

    fun create(user: Announcements): Id<Announcements>? {
        announcementsCollection.insertOne(user)
        client.close()
        return user.id
    }

    fun findAll(): List<Announcements> {
        val result = announcementsCollection.find().toList()
        client.close()
        return result
    }

    fun findByID(id: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_ID eq id
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        client.close()
        return result
    }

    fun findByISBN(isbn: Long): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_Book eq isbn
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        client.close()
        return result
    }

    fun findByUser(UserID: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_User eq UserID
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        client.close()
        return result
    }
}