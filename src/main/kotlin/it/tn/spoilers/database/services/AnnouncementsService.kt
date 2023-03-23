package it.tn.spoilers.database.services


import it.tn.spoilers.database.models.Announcements
import it.tn.spoilers.database.models.AnnouncementsData
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
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


    /**
     * Update the expire date of a specific announcement in the database
     *
     * @author Mirco Sottovia, Matteo Lunelli
     * @param Id[String] announcement id, ExpireDate[String]  announcement ExpireDate
     * @return
     */
    fun updateExpireDateById(Id : String, Expire_Date : String) {
        announcementsCollection.updateOneById(
            AnnouncementsData::Announcement_ID eq Id,
            setValue(AnnouncementsData::Announcement_Expire_Date, Expire_Date)
        )

    }


    /**
     * Update the status of a specific announcement in the database
     *
     * @author Mirco Sottovia, Matteo Lunelli
     * @param Id[String] announcement id, Status[String]  announcement status
     * @return
     */
    fun updateStatusById(Id : String, Status : String) {
        announcementsCollection.updateOneById(
            AnnouncementsData::Announcement_ID eq Id,
            setValue(AnnouncementsData::Announcement_Status, Status)
        )
    }


    /**
     * Update the price of a specific announcement in the database
     *
     * @author Mirco Sottovia, Matteo Lunelli
     * @param Id[String] announcement id, Price[Double]  announcement price
     * @return
     */
    fun updatePriceById(Id : String, Price : Double) {
        announcementsCollection.updateOneById(
            AnnouncementsData::Announcement_ID eq Id,
            setValue(AnnouncementsData::Announcement_Price, Price)
        )
    }


    /**
     * Update the description of a specific announcement in the database
     *
     * @author Mirco Sottovia, Matteo Lunelli
     * @param Id[String] announcement id, Description[String]  announcement description
     * @return
     */
    fun updateDescriptionById(Id : String, Description : String) {
        announcementsCollection.updateOneById(
            AnnouncementsData::Announcement_ID eq Id,
            setValue(AnnouncementsData::Announcement_Description, Description)
        )
    }


    /**
     * Delete a specific announcement from the database
     *
     * @author Mirco Sottovia, Matteo Lunelli
     * @param Id[String] the announcement id
     * @return
     */
    fun deleteById(Id : String){
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_User eq Id
        announcementsCollection.deleteOne(caseSensitiveTypeSafeFilter);
    }

}

    //Gestione filesecret
    fun obtainProperty(property : String) : String {
        val prop = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
        return prop.getProperty(property)
    }
}
