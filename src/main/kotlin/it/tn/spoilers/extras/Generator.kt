package it.tn.spoilers.extras

import java.util.*

/**
 * Class for generating a random UUID
 *
 * @author Francesco Masala
 * @return [String] a random UUID
 */
fun generateUUID(): String {
    return UUID.randomUUID().toString()
}