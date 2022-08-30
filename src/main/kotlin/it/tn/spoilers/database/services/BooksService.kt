package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Books
import org.litote.kmongo.*

class BooksService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val booksCollection = database.getCollection<Books>("Books")

    fun create(user: Books): Id<Books>?  {
        booksCollection.insertOne(user)
        return user.id
    }

    fun findAll(): List<Books> =
        booksCollection.find().toList()

    fun findByISBN(isbn: Long): List<Books> {
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        return booksCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }
}