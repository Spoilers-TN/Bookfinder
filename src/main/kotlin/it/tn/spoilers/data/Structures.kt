package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.extras.generateUUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for the user session
 *
 * @author Francesco Masala
 */
data class UserSession(val token: String)

/**
 * Data class for the user data to be received and polished by the google auth api
 *
 * @author Francesco Masala
 */
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

/**
 * Data class for the user data to be stored in the HTML peeble template
 *
 * @author Francesco Masala
 */
@Serializable
data class user(
    val name: String?,
    val surname: String?,
    val photo: String?,
    val id: String?,
    val uuid: String?,
    val email: String?,
    val realm: String?,
    val gsuite: Boolean?,
    val bio: String?
)

/**
 * Data class for the user data to be stored in the HTML peeble template
 *
 * @author Francesco Masala
 */
data class guestuser(
    val name: String?,
    val surname: String?,
    val photo: String?,
    val uuid: String?,
    val bio: String?,
)

/**
 * Data class for the user data to be stored in the HTML peeble template
 *
 * @author Francesco Masala
 */
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
    /**
     * Data cast to the user data class
     *
     * @author Francesco Masala
     */
    fun toData(): Users =
        Users(
            User_ID = this.sub,
            User_UUID = generateUUID(),
            User_School_Domain = "gmail.com",
            User_Name = this.givenName,
            User_Surname = this.familyName.toString(),
            User_Biog = "",
            User_Photo = "https://www.gravatar.com/avatar/${ToMD5(this.email)}",
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
data class book(
    val image: String,
    val seller: String,
    val title: String,
    val status: String
)

@Serializable
data class insertionBook(
    val author: String?,
    val bookName: String?,
    val isbn: Long?
)



@Serializable
data class Error(val code: String, val descr: String, val meme: String)


fun UserInfo.toUsers(): Users =
    Users(
        User_ID = this.sub,
        User_UUID = generateUUID(),
        User_School_Domain = "gmail.com",
        User_Name = this.givenName,
        User_Surname = this.familyName.toString(),
        User_Biog = "",
        User_Photo = "https://www.gravatar.com/avatar/${ToMD5(this.email)}",
        User_Email = this.email,
        User_FullName = this.name,
        User_GSuite = false
    )

