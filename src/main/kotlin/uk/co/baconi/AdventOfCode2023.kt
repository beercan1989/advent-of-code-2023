package uk.co.baconi

import java.io.File

object AdventOfCode2023 {

    fun readResourceByLines(path: String): List<String> {
        return checkNotNull(this::class.java.getResource(path)?.toURI()?.let(::File)?.readLines()) {
            "Resource was not found at: $path"
        }
    }
}