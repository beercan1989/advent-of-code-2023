package uk.co.baconi

// https://adventofcode.com/2023/day/3
fun main() {
    dayThreePartOne()
    dayThreePartTwo()
}

private val dayThreeExample = listOf(
    "467..114..",
    "...*......",
    "..35..633.",
    "......#...",
    "617*......",
    ".....+.58.",
    "..592.....",
    "......755.",
    "...$.*....",
    ".664.598.."
)

fun dayThreePartOne() {

    println()
    println("--- Part One --- ")

    val exampleAnswer = dayThreeExample
        .extractNumbers()
        .findParts(dayThreeExample)
        .sum()

    println("Example answer: $exampleAnswer")

    val engineSchematic = getEngineSchematic()
    val answer = engineSchematic
        .extractNumbers()
        .findParts(engineSchematic)
        .sum()

    println("Answer: $answer")
}

fun dayThreePartTwo() {

    println()
    println("--- Part Two --- ")

    val exampleAnswer = dayThreeExample
        .extractAsteriskIndices()
        .findGearRatios(dayThreeExample)
        .sum()

    println("Example answer: $exampleAnswer")

    val engineSchematic = getEngineSchematic()
    val answer = engineSchematic
        .extractAsteriskIndices()
        .findGearRatios(engineSchematic)
        .sum()

    println("Answer: $answer")
}

fun getEngineSchematic(): List<String> = AdventOfCode2023.readResourceByLines("/day_03.txt")

data class NumberWithPosition(val number: String, val start: Int, val end: Int)

fun List<String>.extractNumbers(): List<List<NumberWithPosition>> = map(String::extractNumbers)

fun String.extractNumbers(): List<NumberWithPosition> {

    val result = mutableListOf<NumberWithPosition>()

    foldIndexed("") { index, accumulated, current ->
        if (current.isDigit() && index < length - 1) return@foldIndexed accumulated + current
        if (accumulated.isEmpty()) return@foldIndexed ""

        if (current.isDigit()) {
            val updated = accumulated + current
            result.add(NumberWithPosition(updated, (index + 1) - updated.length, index + 1))
        } else {
            result.add(NumberWithPosition(accumulated, index - accumulated.length, index))
        }

        return@foldIndexed ""
    }

    return result
}

fun List<String>.extractAsteriskIndices(): List<List<Int>> = map { line ->
    line.mapIndexedNotNull { index, current -> if (current == '*') index else null }
}

fun List<List<NumberWithPosition>>.findParts(source: List<String>): List<Int> = flatMapIndexed { lineNumber, numbers ->

    val lineAbove = if (lineNumber == 0) null else source[lineNumber - 1]
    val lineCurrent = source[lineNumber]
    val lineBelow = if (lineNumber + 1 < source.size) source[lineNumber + 1] else null

    numbers
        .filter { number ->

            val hasSymbolAbove = lineAbove?.hasSymbol(number.start, number.end) ?: false
            val hasSymbolCurrent = lineCurrent.hasSymbol(number.start, number.end)
            val hasSymbolBelow = lineBelow?.hasSymbol(number.start, number.end) ?: false

            hasSymbolAbove || hasSymbolCurrent || hasSymbolBelow
        }
        .map {
            it.number.toInt()
        }
}

fun String.hasSymbol(start: Int, end: Int): Boolean {
    val from = (if (start <= 0) start else start - 1)
    val to = (if (end + 1 < length) end + 1 else end)
    return substring(from..<to).contains(Regex("[^0-9.]"))
}

fun List<List<Int>>.findGearRatios(source: List<String>): List<Int> = flatMapIndexed { lineNumber, asteriskIndices ->

    val lineAbove = (if (lineNumber == 0) null else source[lineNumber - 1]).orEmpty().extractNumbers()
    val lineCurrent = source[lineNumber].extractNumbers()
    val lineBelow = (if (lineNumber + 1 < source.size) source[lineNumber + 1] else null).orEmpty().extractNumbers()

    asteriskIndices.mapNotNull { index ->

        val above = lineAbove.getGearRatioParts(index)
        val current = lineCurrent.getGearRatioParts(index)
        val below = lineBelow.getGearRatioParts(index)

        val parts = above + current + below

        if (parts.isEmpty() || parts.size < 2 || parts.size >= 3) {
            null
        } else {
            val (first, second) = parts
            first * second
        }
    }
}

fun List<NumberWithPosition>.getGearRatioParts(index: Int): List<Int> = mapNotNull { number ->
    if ((index - 1) in number.start..<number.end) number.number.toInt()
    else if (index in number.start..<number.end) number.number.toInt()
    else if ((index + 1) in number.start..<number.end) number.number.toInt()
    else null
}
