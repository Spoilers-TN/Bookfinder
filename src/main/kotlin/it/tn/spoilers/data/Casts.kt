package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.extras.generateUUID

/**
 * This function converts the [UserInfoGSuite] object to a [Users] DB-Compatible object
 *
 * @author Francesco Masala
 * @param InputFromUser - Converts the UserInfoGSuite json object received from the Google API to a [Users] object
 * @return Users
 */
fun CastGsuiteUserToUserDb(InputFromUser: UserInfoGSuite): Users =
    Users(
        null,
        InputFromUser.sub,
        generateUUID(),
        InputFromUser.hd,
        InputFromUser.givenName,
        ifNullPutVoidString(InputFromUser.familyName.toString()),
        "",
        "https://www.gravatar.com/avatar/${ToMD5(InputFromUser.email)}",
        InputFromUser.email,
        InputFromUser.name,
        true
    )

/**
 * This function converts the [UserInfo] object to a [Users] DB-Compatible object
 *
 * @author Francesco Masala
 * @param InputFromUser - Converts the [UserInfo] json object received from the Google API to a [Users] object
 * @return Users
 */
fun CastNormalUserToUserDb(InputFromUser: UserInfo): Users =
    Users(
        null,
        InputFromUser.sub,
        generateUUID(),
        "gmail.com",
        InputFromUser.givenName,
        ifNullPutVoidString(InputFromUser.familyName.toString()),
        "",
        "https://www.gravatar.com/avatar/${ToMD5(InputFromUser.email)}",
        InputFromUser.email,
        InputFromUser.name,
        false
    )

/**
 * Converts the void string into another void string
 *
 * @author Francesco Masala
 * @param String - Converts a void string into another void string
 * @return Tha void
 */
fun ifNullPutVoidString(input: String?): String {
    return if (input == null) "" else input
}
