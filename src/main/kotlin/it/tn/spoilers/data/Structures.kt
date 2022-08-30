package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.plugins.database.toUsers
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UserSession(val token: String)
data class UserData(
    val sub: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String?,
    val picture: String,
    val email: String,
    val email_verified: Boolean,
    val locale: String,
    val hd: String,
    val GSuiteUser: Boolean
)



@Serializable
data class user(
    val name: String?,
    val surname: String?,
    val photo: String?,
    val id: String?,
    val email: String?,
    val realm: String?,
    val gsuite: Boolean?
)

@Serializable
data class UserInfo(
    val sub: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String?,
    val picture: String,
    val email: String,
    val email_verified: Boolean,
    val locale: String
) {
    fun toData(): Users =
        Users(
            User_ID = this.sub,
            User_School_Domain = "gmail.com",
            User_Name = this.givenName,
            User_Surname = this.familyName.toString(),
            User_Biog = "",
            User_Email = this.email,
            User_FullName = this.name,
            User_GSuite = false
        )
}

@Serializable
data class UserInfoGSuite(
    val sub: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String?,
    val picture: String,
    val email: String,
    val email_verified: Boolean,
    val locale: String,
    val hd: String
)

@Serializable
data class MustacheUser(val id: Int, val name: String)

@Serializable
data class book(val image: String, val seller: String, val title: String, val status: String)

@Serializable
data class Error(val code: String, val descr: String, val meme: String)


fun UserInfo.toUsers(): Users =
    Users(
        User_ID = this.sub,
        User_School_Domain = "gmail.com",
        User_Name = this.givenName,
        User_Surname = this.familyName.toString(),
        User_Biog = "",
        User_Email = this.email,
        User_FullName = this.name,
        User_GSuite = false
    )
