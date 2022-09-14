package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Books
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class BooksService {
    private val client = KMongo.createClient("mongodb+srv://<username>:<password>@bookfinder-db.eukircn.mongodb.net/?retryWrites=true&w=majority")
    private val database = client.getDatabase("bookfinder")
    private val booksCollection = database.getCollection<Books>("Books")

    fun create(user: Books): Id<Books>? {
        booksCollection.insertOne(user)
        client.close()
        return user.id

    }

    fun findAll(): List<Books> {
        val result = booksCollection.find().toList()
        client.close()
        return result
    }

    fun findByISBN(isbn: Long): List<Books> {
        val caseSensitiveTypeSafeFilter = Books::Book_ISBN eq isbn
        val result = booksCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        client.close()
        return result
    }
}