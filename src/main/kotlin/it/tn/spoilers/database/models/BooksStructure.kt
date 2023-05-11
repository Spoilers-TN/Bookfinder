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
    val Book_Study_Year: Int,
    val Book_Authors: String,
    val Book_ISBN: Long,
    val Book_SchoolCode: String,
    val Book_Category: String,
    val Book_Publishers: String,
    val Book_Price: Double,
    val Book_YearSelection: String,
    val Book_School_Type: String,
    val Book_Title: String,
    val Book_Volume: String

)

/**
 * Data class for the books table
 *
 * @author Francesco Masala
 */
@Serializable
data class BooksData(
    val id: String? = null,
    val Book_Study_Year: Int,
    val Book_Authors: String,
    val Book_ISBN: Long,
    val Book_SchoolCode: String,
    val Book_Category: String,
    val Book_Publishers: String,
    val Book_Price: Double,
    val Book_YearSelection: String,
    val Book_School_Type: String,
    val Book_Title: String,
    val Book_Volume: String
)
