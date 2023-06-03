    package it.tn.spoilers.database.models

import com.typesafe.config.Optional
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
    @Optional val Book_Publishers: String? = "",
    val Book_Category: String,
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
    @Optional val Book_Publishers: String? = "",
    val Book_Category: String,
    val Book_Study_Year: Int,
)
