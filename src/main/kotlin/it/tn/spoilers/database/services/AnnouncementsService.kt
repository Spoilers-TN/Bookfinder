package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Announcements
import it.tn.spoilers.database.models.AnnouncementsData
import it.tn.spoilers.database.models.Books
import it.tn.spoilers.plugins.database.toAnnouncementsData
import org.litote.kmongo.*
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
    fun findByID(id: String): AnnouncementsData? {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_ID eq id
        val result = announcementsCollection.findOne(caseSensitiveTypeSafeFilter)?.toAnnouncementsData()
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
     * Get all announcements for a user (used by logged-in user)
     *
     * @author Berti, Furlan
     */
    fun findAllByUser(UserID: String): List<Announcements> {
        val caseSensitiveTypeSafeFilter = Announcements::Announcement_User eq UserID
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Get all announcements for a category
     *
     * @author Berti, Furlan
     */
    fun findByCategory(Category :String): List<Announcements> {

        val bServ = BooksService()
        val books = bServ.findByCategory(Category);

        var result = emptyList<Announcements>()
        var isbns = emptyList<Long>()


        for(b:Books in books){
            if(!isbns.contains(b.Book_ISBN)) {
                isbns = isbns.plus(b.Book_ISBN)
                result = result.plus(findByISBN(b.Book_ISBN))
            }
        }

        //client.close()
        return result
    }

    /**
     * Get all announcements for a book name
     *
     * @author Berti, Furlan
     */
    fun findByName(Name :String): List<Announcements> {

        val bServ = BooksService()
        val books = bServ.findByName(Name);

        var result = emptyList<Announcements>()
        var isbns = emptyList<Long>()

        var uniqueBooks = emptyList<Books>()

        for(b:Books in books){ //ISBN UGUALE NON LIBRO INTERO UGUALE
            if(!uniqueBooks.contains(b)) uniqueBooks.plus(b)
        }

        for(b:Books in books){
            if(!isbns.contains(b.Book_ISBN)) {
                isbns = isbns.plus(b.Book_ISBN)
                result = result.plus(findByISBN(b.Book_ISBN))
            }

        }

        //client.close()
        return result
    }

    /**
     * Get all announcements for a book year
     *
     * @author Berti, Furlan
     */
    fun findByYear(Year :Int): List<Announcements> {

        val bServ = BooksService()
        val books = bServ.findByYear(Year);

        var result = emptyList<Announcements>()
        var isbns = emptyList<Long>()

        for(b:Books in books){
            if(!isbns.contains(b.Book_ISBN)) {
                isbns = isbns.plus(b.Book_ISBN)
                result = result.plus(findByISBN(b.Book_ISBN))
            }
        }

        //client.close()
        return result
    }

    /**
     * Get all announcements for a user (used by logged-in user for searching other users)
     *
     * @author Berti, Furlan
     */
    fun findByUser(UserID: String): List<Announcements> {
        val result = announcementsCollection.find(Announcements::Announcement_User eq UserID, Announcements::Announcement_Status eq "Published").toList()
        //client.close()
        return result
    }

    /**
     * Get a specific announcement from the database based of category
     *
     * @author Berti, Furlan
     */
    fun findByPrice(price: Double): List<Announcements> {

        val caseSensitiveTypeSafeFilter = Announcements::Announcement_Price lt price
        val result = announcementsCollection.find(caseSensitiveTypeSafeFilter).toList()
        //client.close()
        return result
    }

    //Gestione filesecret
    fun obtainProperty(property : String) : String {
        val prop = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
        return prop.getProperty(property)
    }
}