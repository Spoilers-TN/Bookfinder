package it.tn.spoilers.database.services

import com.mongodb.client.model.TextSearchOptions
import it.tn.spoilers.database.models.Books
import org.litote.kmongo.*
import org.litote.kmongo.eq
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
    fun findByISBN(isbn: Long): List<Books> {
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        val result = booksCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
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


    /**
     * Get a specific books from the database based on category
     *
     * @author Furlan, Berti
     * @param
     */
    fun findByCategory(category : String): List<Books> {
        val caseSensitiveTypeSafeFilter = Books::Book_Category eq category
        val result = booksCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close
        //()
        return result
    }

    /**
     * Get a specific books from the database based on name
     *
     * @author Furlan, Berti
     * @param
     */
    fun findByName(name : String): List<Books> {

        val result = booksCollection.find(text(name, TextSearchOptions().caseSensitive(false).diacriticSensitive(false))).toList()
        return result
    }


    /**
     * Get a specific books from the database based on study year
     *
     * @author Furlan, Berti
     * @param
     */
    fun findByYear(year : Int): List<Books> {
        val caseSensitiveTypeSafeFilter = Books::Book_Study_Year eq year
        val result = booksCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close
        //()
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