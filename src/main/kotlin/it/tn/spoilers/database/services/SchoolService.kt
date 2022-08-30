package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.School
import org.litote.kmongo.*

class SchoolService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val schoolCollection = database.getCollection<School>("School")

    fun create(school: School): Id<School>?  {
        schoolCollection.insertOne(school)
        return school.id
    }

    fun findAll(): List<School> =
        schoolCollection.find().toList()

    fun findByCode(code: String): List<School> {
        val caseSensitiveTypeSafeFilter = School::School_Code regex code
        return schoolCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
    }
}