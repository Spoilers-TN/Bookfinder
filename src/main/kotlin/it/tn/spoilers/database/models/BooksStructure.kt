package it.tn.spoilers.database.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Data class for the books table
 *
 * @author Francesco Masala
 */
@Serializable
data class Books(
    @BsonId
    val id: Id<Books>? = null,
    val Book_ISBN: Long,
    val Book_Title: String,
    val Book_Author: String,
    val Book_Publishers: String,
    val Book_Year: String,
    val Book_Study_Year: Int,
)

/**
 * Data class for the books table
 *
 * @author Francesco Masala
 */
@Serializable
data class BooksData(
    val id: String? = null,
    val Book_ISBN: Long,
    val Book_Title: String,
    val Book_Author: String,
    val Book_Publishers: String,
    val Book_Year: String,
    val Book_Study_Year: Int,
)
