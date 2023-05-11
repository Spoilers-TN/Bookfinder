package it.tn.spoilers.database.services


import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import it.tn.spoilers.database.models.*
import it.tn.spoilers.extras.generateUUID
import it.tn.spoilers.plugins.database.toAnnouncementsData
import it.tn.spoilers.plugins.database.toBooksData
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.*
import java.time.LocalDate
import java.util.*

/**
 * Service for the announcements table in the database
 *
 * @author Francesco Masala
 */
class AnnouncementsService {

    private val client = KMongo.createClient(obtainProperty("my.clientDb"))
    private val database = client.getDatabase(obtainProperty("my.database"))
    private val announcementsCollection = database.getCollection<Announcements>("Announcements")


    /**
     * Create an announcement in the database
     *
     * @author Francesco Masala
     * @param announcement[Announcements] the announcement to create
     * @return the announcement ID
     */
    fun create(user: Announcements): Id<Announcements>? {
        announcementsCollection.insertOne(user)
        //client.close()
        return user.id
    }

    /**
     * Insert data into announcement
     *
     * @author Tiziano Dalri, Alessio Cristoforetti
     */

    fun assistedCreate(Announcement_User: String, Announcement_Book: Long, Announcement_Status: String,Announcement_Price: Double,
                       Announcement_Book_Status: String, Announcement_Description: String, Announcement_Ebook: Boolean) {

        val announcement = Announcements(
            Announcement_ID = generateUUID(),
            Announcement_User = Announcement_User,
            Announcement_Book = Announcement_Book,
            Announcement_Publish_Date = LocalDate.now().toString(),
            Announcement_Expire_Date =  LocalDate.now().plusDays(30).toString(),
            Announcement_Status = Announcement_Status,
            Announcement_Price = Announcement_Price,
            Announcement_Book_Status = Announcement_Book_Status,
            Announcement_Description = Announcement_Description,
            Announcement_Ebook = Announcement_Ebook
        )
        this.create(announcement)
    }



    /**
     * Get all the announcements from the database
     *
     * @author Francesco Masala
     * @return the announcement
     */
    fun findAll(): List<Announcements> {
        val result = announcementsCollection.find().toList()
        //client.close()
        return result
    }

    /**
     * Get a specific announcement from the database
     *
     * @author Francesco Masala
     * @param id[String] the announcement id
     * @return [List] the announcement
     */
    fun findByID(id: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_ID eq id
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }


    /**
     * Get a specific announcement from the database
     *
     * @author Dalri Tiziano, Cristoforetti Alessio
     * @param id[String] the announcement id
     * @return [List] the announcement
     */
    fun findBySpecificID(id: String): AnnouncementsData?{
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_ID eq id
        val result = announcementsCollection.findOne(caseSensitiveTypeSafeFilter)?.toAnnouncementsData()
        //client.close
        //()
        return result
    }

    /**
     * Modify a specific announcement from the database
     *
     * @author Dalri Tiziano, Cristoforetti Alessio
     * @param id[String] the announcement id
     */
    //guarda che strunzata
    fun modifyBySpecificID(id: String, price: Double , bookStatus: String, description: String, eBook: Boolean) {
        announcementsCollection.updateOne(
            Announcements::Announcement_ID eq id,
            combine(
                setValue(Announcements::Announcement_Price, price),
                setValue(Announcements::Announcement_Book_Status, bookStatus),
                setValue(Announcements::Announcement_Description, description),
                setValue(Announcements::Announcement_Ebook, eBook)
            )
        )
    }


    /**
     * Get a specific announcement from the database
     *
     * @author Francesco Masala
     * @param isbn[Long] the announcement ISBN
     * @return [List] the announcement
     */
    fun findByISBN(isbn: Long): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_Book eq isbn
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Get a specific announcement from the database
     *
     * @author Francesco Masala
     * @param UserID[String] the announcement user
     * @return [List] the announcement
     */
    fun findByUser(UserID: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_User eq UserID
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    fun deleteBySpecificId(id: String){
        announcementsCollection.deleteOne(Announcements::Announcement_ID eq id)
    }


    //Gestione filesecret
    fun obtainProperty(property : String) : String {
        val prop = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
        return prop.getProperty(property)
    }
}