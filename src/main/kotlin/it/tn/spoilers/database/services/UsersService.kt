package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.notifications.email.EmailSender
import it.tn.spoilers.plugins.database.toUsersData
import org.litote.kmongo.*

/**
 * Service for the users table
 *
 * @author Francesco Masala
 */
class UsersService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val usersCollection = database.getCollection<Users>("Users")

    /**
     * Create a user in the database if it doesn't exist and email the user
     *
     * @author Francesco Masala
     * @param user[Users] the user to create
     * @return [Id] the user ID
     */
    fun createIfNotPresent(user: Users): Id<Users>? {
        if (checkPresenceByGoogleID(user.User_ID)) {
            //client.close()
            return null
        } else {
            usersCollection.insertOne(user)
            EmailSender().sendEmail(
                user.User_Email, "Benvenuto su BookFinder!",
                "Si, effettivamente dovrei cercare un testo migliore ma shh, Benvenuto su bookfinder!\n" +
                        "Ps: Puoi vedere il tuo bellissimo profilo su https://bookfinder.spoilers.tn.it/user/${
                            this.ReturnUserByID(
                                user.User_ID
                            ).User_UUID
                        }"
            )
            //client.close()
            return user.id
        }
    }

    /**
     * Create a user in the database
     *
     * @author Francesco Masala
     * @param User[Users] the user to create
     * @return [Id] the user ID
     */
    fun create(user: Users): Id<Users>? {
        usersCollection.insertOne(user)
        //client.close()
        return user.id
    }

    /**
     * Update a user biography in the database
     *
     * @author Francesco Masala
     * @param UserUUID[String] the user to update
     * @param userBio[String] the biography to update
     */
    fun updateUserBio(UserUUID: String, userBio: String) {
        usersCollection.updateOne(
            Users::User_UUID eq UserUUID,
            setValue(Users::User_Biog, userBio)
        )
        //client.close()
    }

    /**
     * Return all the users from the database
     *
     * @author Francesco Masala
     * @return [List] the users
     */
    fun findAll(): List<Users> {
        val result = usersCollection.find().toList()
        //client.close()
        return result
    }


    /**
     * Return a specific user from the database by the email address
     *
     * @author Francesco Masala
     * @param email[String] the user id
     * @return [List] the users
     */
    fun findByEmail(email: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_Email regex email
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Return a specific user from the database by the google gsuite domain
     *
     * @author Francesco Masala
     * @param domain[String] the domain
     * @return [List] the users
     */
    fun findByDomain(domain: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_School_Domain regex domain
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Return a specific user from the database by the google id
     *
     * @author Francesco Masala
     * @param id[String] the user id
     * @return [List] the users
     */
    fun findByGoogleID(googleID: String): List<Users> {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        val result = usersCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Return a specific user from the database and makes it peeble-compatibale
     *
     * @author Francesco Masala
     * @param googleID[String] the user UUID
     * @return [UsersData] the user
     */
    fun ReturnUserByID(googleID: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex googleID
        val result = usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
        //client.close()
        return result
    }

    /**
     * Return a specific user from the database by the UUID
     *
     * @author Francesco Masala
     * @param uuid[String] the user UUID
     * @return [List] the users
     */
    fun ReturnUserByUUID(uuid: String): UsersData {
        val caseSensitiveTypeSafeFilter = Users::User_UUID regex uuid
        val result = usersCollection.findOne(caseSensitiveTypeSafeFilter)!!.toUsersData()
        //client.close()
        return result
    }

    /**
     * Check if a user is present in the database by the google id
     *
     * @author Francesco Masala
     * @param googleID[String] the user UUID
     * @return [Boolean] the users
     */
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

    /**
     * Check if a user is present in the database by the google id
     *
     * @author Dalri Tiziano, Kevin Vargas, Chen Qiang
     * @param id[String] the user id
     */
    fun deleteById(id: String) {
        val caseSensitiveTypeSafeFilter = Users::User_ID regex id
        usersCollection.deleteOne(caseSensitiveTypeSafeFilter)
        //client.close()
    }
}
