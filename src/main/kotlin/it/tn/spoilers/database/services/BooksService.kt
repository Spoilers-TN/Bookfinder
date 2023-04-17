package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.models.BooksData
import it.tn.spoilers.plugins.database.toBooksData
import org.litote.kmongo.*
import java.util.*

/**
 * Service for the books table in the database
 *
 * @author Francesco Masala
 */
class BooksService {

    private val client = KMongo.createClient(obtainProperty("my.clientDb"))
    private val database = client.getDatabase(obtainProperty("my.database"))
    private val booksCollection = database.getCollection<Books>("Books")

    /**
     * Create a book in the database
     *
     * @author Francesco Masala
     * @param book[Books] the book to create
     * @return the book ID
     */
    fun create(user: Books): Id<Books>? {
        booksCollection.insertOne(user)
        //client.close()
        return user.id
    }

    /**
     * Get all the books from the database
     *
     * @author Francesco Masala
     * @return [List] book
     */
    fun findAll(): List<Books> {
        val result = booksCollection.find().toList()
        //client.close()
        return result
    }

    /**
     * Get a specific book from the database
     *
     * @author Francesco Masala
     * @param isbn[Long] the book isbn
     * @return [List] the book
     */
    fun findByISBN(isbn: Long): List<Books>{
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        val result = booksCollection.find(caseSensitiveTypeSafeFilter).toList()
        //client.close
        //()
        return result
    }

    fun findBySpecificISBN(isbn: Long): BooksData?{
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        val result = booksCollection.findOne(caseSensitiveTypeSafeFilter)?.toBooksData()
        //client.close
        //()
        return result
    }

    /**
     * Erase a book instance
     *
     * @author Roberto Pozzi
     * @param isbn[Long] the book's isbn
     */
    fun deleteByISBN(isbn: Long) {
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        booksCollection.deleteOne(caseSensitiveTypeSafeFilter)
        //client.close()
    }

    //Gestione filesecret
    fun obtainProperty(property : String) : String {
        val prop = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
        return prop.getProperty(property)
    }


}