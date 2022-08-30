package it.tn.spoilers.database.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Books(
    @BsonId
    val id: Id<Books>? = null,
    val Book_ISBN: Long,
    val Book_Title: String,
    val Book_Author: String,
    val Book_Publishers: String,
    val Book_Year: String,
    val Book_Digital: Boolean,
    val Book_Edition: Int,
    val Book_Study_Year: Int,
    val Book_Price: Double
)
 @Serializable
data class BooksData(
    val id: String? = null,
    val Book_ISBN: Long,
    val Book_Title: String,
    val Book_Author: String,
    val Book_Publishers: String,
    val Book_Year: String,
    val Book_Digital: Boolean,
    val Book_Edition: Int,
    val Book_Study_Year: Int,
    val Book_Price: Double
)
