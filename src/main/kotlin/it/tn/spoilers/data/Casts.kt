package it.tn.spoilers.data

import it.tn.spoilers.database.models.Users

fun CastGsuiteUserToUserDb(InputFromUser: UserInfoGSuite) : Users =
    Users(
        null,
        InputFromUser.sub,
        InputFromUser.hd,
        InputFromUser.givenName,
        InputFromUser.familyName.toString(),
        "",
        InputFromUser.email,
        InputFromUser.name,
        true
    )

fun CastNormalUserToUserDb(InputFromUser: UserInfo) : Users =
    Users(
        null,
        InputFromUser.sub,
        "gmail.com",
        InputFromUser.givenName,
        InputFromUser.familyName.toString(),
        "",
        InputFromUser.email,
        InputFromUser.name,
        false
    )