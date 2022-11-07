package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Reviews
import it.tn.spoilers.extras.generateUUID
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

/**
 * Service for the reviews table in the database
 *
 * @author Francesco Masala
 */
class ReviewsService {
    private val client = KMongo.createClient("")
    private val database = client.getDatabase("bookfinder")
    private val reviewsCollection = database.getCollection<Reviews>("Reviews")

    /**
     * Create a review in the database
     *
     * @author Francesco Masala
     * @param review[Reviews] the review to create
     * @return the review ID
     */
    fun create(review: Reviews): Id<Reviews>? {
        reviewsCollection.insertOne(review)
        //client.close()
        return review.id

    }

    /**
     * Tbh I don't remember what this does
     *
     * @author Francesco Masala
     */
    fun assistedCreate(title: String, message: String, sender: String, recipient: String): Id<Reviews>? {
        val review = Reviews(null, generateUUID(), title, message, sender, recipient, "", "")
        this.create(review)
        //client.close()
        return review.id

    }

    /**
     * Get all the reviews created for a specific user
     *
     * @author Francesco Masala
     * @param userID[String] the recipient of the reviews
     * @return [List] review
     */
    fun findByRecipient(userID: String): List<Reviews> {
        val caseSensitiveTypeSafeFilter = Reviews::Review_Recipient eq userID
        val result = reviewsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }

    /**
     * Get all the reviews created by a specific user
     *
     * @author Francesco Masala
     * @param userID[String] the sender of the reviews
     * @return [List] review
     */
    fun findBySender(userID: String): List<Reviews> {
        val caseSensitiveTypeSafeFilter = Reviews::Review_Sender eq userID
        val result = reviewsCollection.find(caseSensitiveTypeSafeFilter)
            .toList()
        //client.close()
        return result
    }
}