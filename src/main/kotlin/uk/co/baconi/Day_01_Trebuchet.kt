package uk.co.baconi

// https://adventofcode.com/2023/day/1
fun main() {
    dayOnePartOne()
    dayOnePartTwo()
}

fun dayOnePartOne() {

    println()
    println("--- Part One --- ")

    val example = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet"
    )

    val exampleAnswer = example
        .extractFirstAndLastDigit()
        .sum()

    println("Example answer: $exampleAnswer")

    val calibrationAnswer = getCalibrationDocument()
        .extractFirstAndLastDigit()
        .sum()

    println("Calibration answer: $calibrationAnswer")
}


fun dayOnePartTwo() {

    println()
    println("--- Part Two --- ")

    val example = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen"
    )

    val exampleAnswer = example
        .replaceDigitWordsWithDigits()
        .extractFirstAndLastDigit()
        .sum()

    println("Example answer: $exampleAnswer")

    val calibrationAnswer = getCalibrationDocument()
        .replaceDigitWordsWithDigits()
        .extractFirstAndLastDigit()
        .sum()

    println("Calibration answer: $calibrationAnswer")
}

fun getCalibrationDocument(): List<String> = AdventOfCode2023.readResourceByLines("/day_01.txt")

fun List<String>.extractFirstAndLastDigit(): List<Int> = map { line ->
    val first = line.first(Char::isDigit)
    val last = line.last(Char::isDigit)
    "$first$last".toInt()
}

fun List<String>.replaceDigitWordsWithDigits(): List<String> = map { line ->
    line.fold("") { accumulated, current ->
        if (current.isDigit()) {
            accumulated + current
        } else {
            (accumulated + current)
                .replace("one", "o1e")
                .replace("two", "t2o")
                .replace("three", "t3ree")
                .replace("four", "f4ur")
                .replace("five", "f5ve")
                .replace("six", "s6x")
                .replace("seven", "s7ven")
                .replace("eight", "e8ght")
                .replace("nine", "n9ne")
        }
    }
}
