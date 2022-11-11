package it.tn.spoilers.plugins.database

import it.tn.spoilers.data.ToMD5
import it.tn.spoilers.data.UserInfo
import it.tn.spoilers.data.UserInfoGSuite
import it.tn.spoilers.database.models.Users
import it.tn.spoilers.database.models.UsersData
import it.tn.spoilers.extras.generateUUID

fun Users.toUsersData(): UsersData =
    UsersData(
        id = this.id.toString(),
        User_ID = this.User_ID,
        User_UUID = this.User_UUID,
        User_School_Domain = this.User_School_Domain,
        User_Name = this.User_Name,
        User_Surname = this.User_Surname,
        User_Biog = this.User_Biog,
        User_Photo = "https://www.gravatar.com/avatar/${ToMD5(this.User_Email)}",
        User_Email = this.User_Email,
        User_FullName = this.User_FullName,
        User_GSuite = this.User_GSuite,
    )

fun UsersData.toUsers(): Users =
    Users(
        User_ID = this.User_ID,
        User_UUID = this.User_UUID,
        User_School_Domain = this.User_School_Domain,
        User_Name = this.User_Name,
        User_Surname = this.User_Surname,
        User_Biog = this.User_Biog,
        User_Photo = "https://www.gravatar.com/avatar/${ToMD5(this.User_Email)}",
        User_Email = this.User_Email,
        User_FullName = this.User_FullName,
        User_GSuite = this.User_GSuite,
    )

fun UserInfoGSuite.toUsers(): Users =
    Users(
        User_ID = this.sub,
        User_UUID = generateUUID(),
        User_School_Domain = this.hd,
        User_Name = this.givenName,
        User_Surname = this.familyName.toString(),
        User_Biog = "",
        User_Photo = "https://www.gravatar.com/avatar/${ToMD5(this.email)}",
        User_Email = this.email,
        User_FullName = this.name,
        User_GSuite = true
    )

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

