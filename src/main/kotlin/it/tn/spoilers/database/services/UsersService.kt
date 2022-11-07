package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.notifications.email.EmailSender
import it.tn.spoilers.plugins.database.toUsersData
import org.litote.kmongo.*

class UsersService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val usersCollection = database.getCollection<Users>("Users")

    fun createIfNotPresent(user: Users): Id<Users>? {
        if (checkPresenceByGoogleID(user.User_ID)) {
            //client.close()
            return null
        } else {
            usersCollection.insertOne(user)
            EmailSender().sendEmail(user.User_Email, "Benvenuto su BookFinder!",
                "Si, effettivamente dovrei cercare un testo migliore ma shh, Benvenuto su bookfinder!\n" +
                        "Ps: Puoi vedere il tuo bellissimo profilo su https://bookfinder.spoilers.tn.it/user/${this.ReturnUserByID(user.User_ID).User_UUID}")
            //client.close()
            return user.id
        }
    }

    fun create(user: Users): Id<Users>? {
        usersCollection.insertOne(user)
        //client.close()
        return user.id
    }

    fun updateUserBio(userID: String, userBio: String) {
        usersCollection.updateOne(
            Users::User_ID eq userID,
            setValue(Users::User_Biog, userBio)
        )
        //client.close()
    }

    fun findAll(): List<Users> {
        val result = usersCollection.find().toList()
        //client.close()
        return result
    }


    fun findByEmail(email: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_Email regex email
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    fun findByDomain(domain: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_School_Domain regex domain
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    fun findByGoogleID(googleID: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    fun ReturnUserByID(googleID: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        val result = usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
        //client.close()
        return result
    }

    fun ReturnUserByUUID(uuid: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_UUID regex uuid
        val result = usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
        //client.close()
        return result
    }

    fun checkPresenceByGoogleID(googleID: String): Boolean {
        try {
            val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
            if (usersCollection.find(caseSensitiveTypeSafeFilter).toList().isNotEmpty()) {
                //client.close()
                return true
            }
        } catch (e: Exception) {
            //client.close()
            return false
        }
        //client.close()
        return false
    }
}