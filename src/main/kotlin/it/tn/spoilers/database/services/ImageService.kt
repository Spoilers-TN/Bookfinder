package it.tn.spoilers.database.services

import it.tn.spoilers.database.models.Image
import it.tn.spoilers.plugins.database.toImageData
import org.bson.types.Binary
import org.litote.kmongo.*
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.util.*

/**
 * Service for the 'Image' collection in the database.
 * Handles operations like inserting, finding and encoding images.
 *
 * <p>Uses KMongo to connect to a MongoDB instance.
 * Provides methods to interact with the 'Image' collection in the database.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>{@code
 * val imageService = ImageService()
 * val imageStream = File("path/to/image.jpg").inputStream()
 * imageService.create("myImage.jpg", imageStream)
 * }</pre>
 *
 * @author Furlan, Dalri
 */
class ImageService {

    private val client = KMongo.createClient(obtainProperty("my.clientDb"))
    private val database = client.getDatabase(obtainProperty("my.database"))
    val col = database.getCollection<Image>("Image")

    /**
     * Insert an image into the database with the given file name and input stream.
     *
     * @param fileName the name of the file to be inserted
     * @param inputStream the input stream of the file to be inserted
     */
    fun create(fileName: String, inputStream: InputStream) {
        val binaryFile = Image(
            id = null,
            filename = fileName,
            content = inputStreamToBinary(inputStream)
        )
        col.insertOne(binaryFile)
    }

    /**
     * Convert an input stream to a binary object.
     *
     * @param inputStream the input stream to be converted
     * @return a binary object
     */
    fun inputStreamToBinary(inputStream: InputStream): Binary {
        return Binary(inputStream.readBytes())
    }

    /**
     * Get an input stream for an image with the given file name.
     *
     * @param fileName the name of the file for the image to retrieve
     * @return an input stream for the image
     */
    fun getByFileName(fileName: String): InputStream{
        val caseSensitiveTypeSafeFilter = Image::filename eq fileName
        val result = col.findOne(caseSensitiveTypeSafeFilter)?.toImageData()


        if(result?.content?.data?.size!! <= 1){
            return File("src/main/resources/assets/img/general/notfound.webp").inputStream();
        }else{
            return ByteArrayInputStream(result.content.data)
        }
    }

    /**
     * Encode an image to base64 format.
     *
     * @param inputStream the input stream of the image to be encoded
     * @return a base64-encoded string representing the image
     */
    fun encodeImageToBase64(inputStream: InputStream): String{
        val bytes = inputStream.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }

    /**

    This function obtains the value of a property from the application.properties file

    @param property the name of the property to obtain
    @return the value of the specified property as a String
    @author Furlan, Dalri
     */
    private fun obtainProperty(property : String) : String {
        val prop = Properties()
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        prop.load(inputStream)
        return prop.getProperty(property)
    }
}