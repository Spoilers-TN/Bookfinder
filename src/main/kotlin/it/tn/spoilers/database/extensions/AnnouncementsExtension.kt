package it.tn.spoilers.plugins.database

import it.tn.spoilers.database.models.Announcements
import it.tn.spoilers.database.models.AnnouncementsData

fun Announcements.toAnnouncementsData(): AnnouncementsData =
    AnnouncementsData(
        id = this.id.toString(),
        Announcement_ID = this.Announcement_ID,
        Announcement_User = this.Announcement_User,
        Announcement_Book = this.Announcement_Book,
        Announcement_Publish_Date = this.Announcement_Publish_Date,
        Announcement_Expire_Date = this.Announcement_Expire_Date,
        Announcement_Status = this.Announcement_Status,
        Announcement_Price = this.Announcement_Price,
        Announcement_Book_Status = this.Announcement_Book_Status,
        Announcement_Description = this.Announcement_Description,
        Announcement_Ebook = this.Announcement_Ebook
    )

fun AnnouncementsData.toAnnouncements(): Announcements =
    Announcements(
        Announcement_ID = this.Announcement_ID,
        Announcement_User = this.Announcement_User,
        Announcement_Book = this.Announcement_Book,
        Announcement_Publish_Date = this.Announcement_Publish_Date,
        Announcement_Expire_Date = this.Announcement_Expire_Date,
        Announcement_Status = this.Announcement_Status,
        Announcement_Price = this.Announcement_Price,
        Announcement_Book_Status = this.Announcement_Book_Status,
        Announcement_Description = this.Announcement_Description,
        Announcement_Ebook = this.Announcement_Ebook
    )