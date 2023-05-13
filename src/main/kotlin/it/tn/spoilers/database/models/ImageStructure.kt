package it.tn.spoilers.database.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.Binary


/**
 * Data class for the books table
 *
 * @author Francesco Masala
 */
@Serializable
data class Image(
    @BsonId
    val id: String? = null,
    val filename: String,
    @Contextual
    val content: Binary
)

/**
 * Data class for the books table
 *
 * @author Francesco Masala
 */
@Serializable
data class ImageData(
    @BsonId
    val id: String? = null,
    val filename: String,
    @Contextual
    val content: Binary
)
