package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.School
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.regex

/**
 * Service for the school table in the database
 *
 * @author Francesco Masala
 */
class SchoolService {
    private val client =
        KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val schoolCollection = database.getCollection<School>("School")

    /**
     * Create a school in the database
     *
     * @author Francesco Masala
     * @param school[School] the school to create
     * @return [Id] the school ID
     */
    fun create(school: School): Id<School>? {
        schoolCollection.insertOne(school)
        //client.close()
        return school.id
    }

    /**
     * Get all the schools from the database
     *
     * @author Francesco Masala
     * @return [List] school
     */
    fun findAll(): List<School> {
        val result = schoolCollection.find().toList()
        //client.close()
        return result
    }

    /**
     * Get a specific school from the database
     *
     * @author Francesco Masala
     * @param code[String] the school code
     * @return [List] the school
     */
    fun findByCode(code: String): List<School> {
        val caseSensitiveTypeSafeFilter = School::School_Code regex code
        val result = schoolCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }
}