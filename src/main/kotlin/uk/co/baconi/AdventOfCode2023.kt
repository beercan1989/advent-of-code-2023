package uk.co.baconi

object AdventOfCode2023 {

    fun readResourceByLines(path: String): List<String> {
        return checkNotNull(this::class.java.getResourceAsStream(path)).reader().readLines()
    }
}