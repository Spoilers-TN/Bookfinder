package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Books
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

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

    fun findById(id: String): Books? {
        val bsonId: Id<Books> = ObjectId(id).toId()
        return booksCollection
            .findOne(Books::id eq bsonId)
    }
}