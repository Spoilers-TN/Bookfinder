package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Books
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

/**
 * Service for the books table in the database
 *
 * @author Francesco Masala
 */
class BooksService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
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
        //client.close()
        return result
    }
}