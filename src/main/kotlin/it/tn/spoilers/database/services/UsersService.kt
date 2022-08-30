package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Users
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class UsersService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val usersCollection = database.getCollection<Users>("Users")

    fun create(user: Users): Id<Users>?  {
        usersCollection.insertOne(user)
        return user.id
    }

    fun findAll(): List<Users> =
        usersCollection.find().toList()

    fun findById(id: String): Users? {
        val bsonId: Id<Users> = ObjectId(id).toId()
        return usersCollection
            .findOne(Users::id eq bsonId)
    }
}