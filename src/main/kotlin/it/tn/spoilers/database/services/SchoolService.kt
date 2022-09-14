package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.School
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.regex

class SchoolService {
    private val client = KMongo.createClient("mongodb+srv://bookfinder:BeJbK4clinNm8J41@bookfinder-db.eukircn.mongodb.net/?retryWrites=true&w=majority")
    private val database = client.getDatabase("bookfinder")
    private val schoolCollection = database.getCollection<School>("School")

    fun create(school: School): Id<School>? {
        schoolCollection.insertOne(school)
        client.close()
        return school.id
    }

    fun findAll(): List<School> {
        val result = schoolCollection.find().toList()
        client.close()
        return result
    }

    fun findByCode(code: String): List<School> {
        val caseSensitiveTypeSafeFilter = School::School_Code regex code
        val result = schoolCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        client.close()
        return result
    }
}