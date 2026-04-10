package com.ridehelper

object RideParser {

    fun parse(texts: List<String>): Map<String, String> {
        val joined = texts.joinToString(" ")

        val price = Regex("R\\$\\s?\\d+[\\.,]?\\d*").find(joined)?.value ?: ""
        val distance = Regex("\\d+[\\.,]?\\d*\\s?km").find(joined)?.value ?: ""
        val rating = Regex("\\d\\.\\d").find(joined)?.value ?: ""

        return mapOf(
            "price" to price,
            "distance" to distance,
            "rating" to rating
        )
    }
}
