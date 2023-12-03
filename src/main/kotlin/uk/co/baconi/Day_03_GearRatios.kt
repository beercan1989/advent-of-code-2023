package uk.co.baconi

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

    val exampleAnswer = ""
    println("Example answer: $exampleAnswer")

    val answer = ""
    println("Answer: $answer")
}

fun getEngineSchematic(): List<String> = AdventOfCode2023.readResourceByLines("/day_03.txt")

data class NumberWithPosition(val number: String, val start: Int, val end: Int)

fun List<String>.extractNumbers(): List<List<NumberWithPosition>> = map { line ->

    val result = mutableListOf<NumberWithPosition>()

    line.foldIndexed("") { index, accumulated, current ->
        if(current.isDigit() && index < line.length - 1) return@foldIndexed accumulated + current
        if(accumulated.isEmpty()) return@foldIndexed ""

        if(current.isDigit()) {
            val updated = accumulated + current
            result.add(NumberWithPosition(updated, (index + 1) - updated.length, index + 1))
        } else {
            result.add(NumberWithPosition(accumulated, index - accumulated.length, index))
        }

        return@foldIndexed ""
    }

    result
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
