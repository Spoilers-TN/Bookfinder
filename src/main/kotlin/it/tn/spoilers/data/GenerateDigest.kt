package it.tn.spoilers.data

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Creates a MD5 digest from a string
 *
 *
 * @author Francesco Masala
 * @param [String] - A simple string
 * @return [String] - The MD5 digest of the string
 */
fun ToMD5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0').lowercase()
}