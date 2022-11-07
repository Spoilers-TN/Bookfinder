package it.tn.spoilers.database.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Reviews(
    @BsonId
    val id: Id<Reviews>? = null,
    val Review_ID: String,
    val Review_Title: String,
    val Review_Message: String,
    val Review_Sender: String,
    val Review_Recipient: String,
    val Review_Sender_Name: String,
    val Review_Sender_Photo: String
)

@Serializable
data class ReviewsData(
    val id: String? = null,
    val Review_ID: String,
    val Review_Title: String,
    val Review_Message: String,
    val Review_Sender: String,
    val Review_Recipient: String,
    val Review_Sender_Name: String,
    val Review_Sender_Photo: String
)