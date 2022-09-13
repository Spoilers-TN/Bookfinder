package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users
import it.tn.spoilers.extras.generateUUID
import it.tn.spoilers.plugins.database.toUsersData

fun CastGsuiteUserToUserDb(InputFromUser: UserInfoGSuite) : Users =
    Users(
        null,
        InputFromUser.sub,
        generateUUID(),
        InputFromUser.hd,
        InputFromUser.givenName,
        InputFromUser.familyName.toString(),
        "",
        "https://www.gravatar.com/avatar/${ToMD5(InputFromUser.email)}",
        InputFromUser.email,
        InputFromUser.name,
        true
    )

fun CastNormalUserToUserDb(InputFromUser: UserInfo) : Users =
    Users(
        null,
        InputFromUser.sub,
        generateUUID(),
        "gmail.com",
        InputFromUser.givenName,
        InputFromUser.familyName.toString(),
        "",
        "https://www.gravatar.com/avatar/${ToMD5(InputFromUser.email)}",
        InputFromUser.email,
        InputFromUser.name,
        false
    )