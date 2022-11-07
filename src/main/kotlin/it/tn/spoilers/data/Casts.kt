package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.extras.generateUUID

fun CastGsuiteUserToUserDb(InputFromUser: UserInfoGSuite): Users =
    Users(
        null,
        InputFromUser.sub,
        generateUUID(),
        InputFromUser.hd,
        InputFromUser.email,
        ifNullPutVoidString(InputFromUser.familyName.toString()),
        "",
        "https://www.gravatar.com/avatar/${ToMD5(InputFromUser.email)}",
        InputFromUser.email,
        InputFromUser.name,
        true
    )

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

fun ifNullPutVoidString(input: String?): String {
    return if (input == null) "" else input
}
