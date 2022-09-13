package it.tn.spoilers.database.services

import com.mongodb.client.FindIterable
import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.plugins.database.toUsersData
import org.litote.kmongo.*

class UsersService {
    private val client = KMongo.createClient("mongodb+srv://<username>:<password>@bookfinder-db.eukircn.mongodb.net/?retryWrites=true&w=majority")
    private val database = client.getDatabase("bookfinder")
    private val usersCollection = database.getCollection<Users>("Users")

    fun createIfNotPresent(user: Users): Id<Users>?  {
        if (checkPresenceByGoogleID(user.User_ID)) {
            return null
        } else {
            usersCollection.insertOne(user)
            return user.id
        }
    }

    fun create(user: Users): Id<Users>?  {
        usersCollection.insertOne(user)
        return user.id
    }

    fun updateUserBio(userID: String, userBio: String){
        usersCollection.updateOne(
            Users::User_ID eq userID,
            setValue(Users::User_Biog, userBio)
        )
    }

    fun findAll(): List<Users> =
        usersCollection.find().toList()

    fun findByEmail(email: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_Email regex email
        return usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }
    fun findByDomain(domain: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_School_Domain regex domain
        return usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }
    fun findByGoogleID(googleID: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        return usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }

    fun ReturnUserByID(googleID: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        return usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
    }

    fun ReturnUserByUUID(uuid: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_UUID regex uuid
        return usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
    }

    fun checkPresenceByGoogleID(googleID: String): Boolean {
        try {
            val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
            if(usersCollection.find(caseSensitiveTypeSafeFilter).toList().isNotEmpty()) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }
}