package it.tn.spoilers.database.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Users(
    @BsonId
    val id: Id<Users>? = null,
    val User_ID: String,
    val User_School_Domain: String,
    val User_Name: String,
    val User_Surname: String,
    val User_Biog: String,
    val User_Email: String,
    val User_FullName: String,
    val User_GSuite: Boolean
)

data class UsersData(
    val id: String? = null,
    val User_ID: String,
    val User_School_Domain: String,
    val User_Name: String,
    val User_Surname: String,
    val User_Biog: String,
    val User_Email: String,
    val User_FullName: String,
    val User_GSuite: Boolean
)
