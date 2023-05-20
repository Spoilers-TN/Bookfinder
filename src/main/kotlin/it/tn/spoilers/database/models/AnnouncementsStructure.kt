package it.tn.spoilers.database.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

/**
 * Data class for the Announcements table
 *
 * @author Francesco Masala
 */
data class Announcements(
    @BsonId
    val id: Id<Announcements>? = null,
    val Announcement_ID: String,
    val Announcement_User: String,
    val Announcement_Book: Long,
    val Announcement_Publish_Date: String,
    val Announcement_Expire_Date: String,
    val Announcement_Status: String,
    val Announcement_Price: Double,
    val Announcement_Book_Status: String,
    val Announcement_Description: String,
    val Announcement_Ebook: Boolean
)

/**
 * Data class for the announcements table
 *
 * @author Francesco Masala
 */
data class AnnouncementsData(
    val id: String? = null,
    val Announcement_ID: String,
    val Announcement_User: String,
    val Announcement_Book: Long,
    val Announcement_Publish_Date: String,
    val Announcement_Expire_Date: String,
    val Announcement_Status: String,
    val Announcement_Price: Double,
    val Announcement_Book_Status: String,
    val Announcement_Description: String,
    val Announcement_Ebook: Boolean
)
