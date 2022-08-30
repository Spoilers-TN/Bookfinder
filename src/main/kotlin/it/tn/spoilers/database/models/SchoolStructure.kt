package it.tn.spoilers.database.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class School(
    @BsonId
    val id: Id<School>? = null,
    val School_Domain: String,
    val School_Name: String,
    val School_City: String,
    val School_Region: String,
    val School_Type: String,
    val School_Code: String
)

@Serializable
data class SchoolData(
    val id: String? = null,
    val School_Domain: String,
    val School_Name: String,
    val School_City: String,
    val School_Region: String,
    val School_Type: String,
    val School_Code: String
)