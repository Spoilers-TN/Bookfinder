package it.tn.spoilers.plugins.database

import it.tn.spoilers.database.models.School
import it.tn.spoilers.database.models.SchoolData

fun School.toSchoolData(): SchoolData =
    SchoolData(
        id = this.id.toString(),
        School_Domain = this.School_Domain,
        School_Name = this.School_Name,
        School_City = this.School_City,
        School_Region = this.School_Region,
        School_Type = this.School_Region,
        School_Code = this.School_Code
    )

fun SchoolData.toSchool(): School =
    School(
        School_Domain = this.School_Domain,
        School_Name = this.School_Name,
        School_City = this.School_City,
        School_Region = this.School_Region,
        School_Type = this.School_Region,
        School_Code = this.School_Code
    )