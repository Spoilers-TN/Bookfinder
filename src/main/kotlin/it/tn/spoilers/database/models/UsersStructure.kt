package it.tn.spoilers.database.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Data class for the users table
 *
 * @author Francesco Masala
 */
@Serializable
data class Users(
    @BsonId
    val id: Id<Users>? = null,
    val User_ID: String,
    val User_UUID: String,
    val User_School_Domain: String,
    val User_Name: String,
    val User_Surname: String,
    val User_Biog: String,
    val User_Photo: String,
    val User_Email: String,
    val User_FullName: String,
    val User_GSuite: Boolean
)

/**
 * Data class for the users table
 *
 * @author Francesco Masala
 */
@Serializable
data class UsersData(
    val id: String? = null,
    val User_ID: String,
    val User_UUID: String,
    val User_School_Domain: String,
    val User_Name: String,
    val User_Surname: String,
    val User_Biog: String,
    val User_Photo: String,
    val User_Email: String,
    val User_FullName: String,
    val User_GSuite: Boolean
)
