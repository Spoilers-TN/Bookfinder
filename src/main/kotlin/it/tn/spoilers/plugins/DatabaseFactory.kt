package it.tn.spoilers.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction

object SchoolTable : Table("School"){
    val School_Domain = text("School_Domain").index("School_Domain_Index", true)
    val School_Name = text("School_Name").index("School_Type_Index", false)
    val School_City = text("School_City")
    val School_Region = text("School_Region")
    val School_Type = text("School_Type")
    override val primaryKey: PrimaryKey = PrimaryKey(School_Domain, name = "School_PK")
}

object UsersTable : Table("Users"){
    val User_ID = varchar("User_ID",200).index("User_ID_Index", true)
    val User_School_Domain = reference("User_School_Domain", SchoolTable.School_Domain, onDelete = null).foreignKey
    val User_Name = text("User_Name")
    val User_Surname = text("User_Surname")
    val User_FullName = text("User_FullName")
    val User_Email = text("User_Email").index("User_Email_Index", true)
    val User_Bio = varchar("User_Bio", 200)
    val User_GSuite = bool("User_GSuite")
    override val primaryKey: PrimaryKey = PrimaryKey(UsersTable.User_ID, name = "User_PK")
    }

object BooksTable : Table("Books"){
    val Book_ISBN = varchar("Book_ISBN", 13).index("Book_ISBN_Index", true)
    val Book_Title = text("Book_Title").index("Book_Title_Index", false)
    val Book_Author = text("Book_Author")
    val Book_Publishers = text("Book_Publishers")
    val Book_Year = integer("Book_Year")
    val Book_Digital = bool("Book_Digital")
    val Book_Edition = integer("Book_Edition")
    val Book_Study_Year = integer("Book_Study_Year")
    val Book_Price = double("Book_Price")
    override val primaryKey: PrimaryKey = PrimaryKey(Book_ISBN, name = "Book_PK")
}

object AnnouncementsTable : Table("Announcements"){
    val Announcement_ID = varchar("Announcement_ID", 200).index("Announcement_ID_Index", true)
    val Announcement_User = reference("Announcement_User", UsersTable.User_ID, onDelete = null).foreignKey
    val Announcement_Book = reference("Announcement_Book", BooksTable.Book_ISBN, onDelete = null).foreignKey
    val Announcement_Publish_Date = date("Announcement_Publish_Date")
    val Announcement_Expire_Date = date("Announcement_Expire_Date").index("Announcement_Expire_Date_Index", false)
    val Announcement_Status = text("Announcement_Status").index("Announcement_Status_Index", false)
    val Announcement_Price = double("Announcement_Price").index("Announcement_Price_Index", false)
    val Announcement_Book_Status = text("Announcement_Book_Status").index("Announcement_Book_Status_Index", false)
    val Announcement_Description = text("Announcement_Description")
    override val primaryKey: PrimaryKey = PrimaryKey(Announcement_ID, name = "Announcement_PK")
}

fun initDb() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:sqlite:spoilers.db"
        username = "sa"
        password = ""
        driverClassName = "org.postgresql.Driver"
        validate()
    }
    Database.connect(HikariDataSource(config))

    transaction {
        SchemaUtils.createMissingTablesAndColumns()
    }
}

