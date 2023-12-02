package uk.co.baconi

import java.io.File


// https://adventofcode.com/2023/day/1
fun main() {
    partOne()
    partTwo()
}

fun partOne() {

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


fun partTwo() {

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
        .extractFirstAndLastDigitIncludingWords()
        .sum()

    println("Example answer: $exampleAnswer")

    val calibrationAnswer = getCalibrationDocument()
        .extractFirstAndLastDigitIncludingWords()
        .sum()

    println("Calibration answer: $calibrationAnswer")
}

fun getCalibrationDocument(): List<String> = checkNotNull(
    AdventOfCode2023::class.java.getResource("/day_01.txt")?.toURI()?.let(::File)?.readLines()
)

fun List<String>.extractFirstAndLastDigit(): List<Int> = map { line ->
    val first = line.first(Char::isDigit)
    val last = line.last(Char::isDigit)
    "$first$last".toInt()
}

fun List<String>.extractFirstAndLastDigitIncludingWords(): List<Int> = map { line ->
    val first = line.firstDigit()
    val last = line.lastDigit()
    "$first$last".toInt()
}

fun String.firstDigit(): Int {
    fold("") { accumulated: String, current: Char ->
        if(current.isDigit()) return current.digitToInt()
        val updated = accumulated + current
        when {
            updated.contains("one") -> return 1
            updated.contains("two") -> return 2
            updated.contains("three") -> return 3
            updated.contains("four") -> return 4
            updated.contains("five") -> return 5
            updated.contains("six") -> return 6
            updated.contains("seven") -> return 7
            updated.contains("eight") -> return 8
            updated.contains("nine") -> return 9
            else -> updated
        }
    }
    throw IndexOutOfBoundsException("No digit found")
}

fun String.lastDigit(): Int {
    foldRight("") { current: Char, accumulated: String ->
        if(current.isDigit()) return current.digitToInt()
        val updated = accumulated + current
        val reversed = updated.reversed()
        when {
            reversed.contains("one") -> return 1
            reversed.contains("two") -> return 2
            reversed.contains("three") -> return 3
            reversed.contains("four") -> return 4
            reversed.contains("five") -> return 5
            reversed.contains("six") -> return 6
            reversed.contains("seven") -> return 7
            reversed.contains("eight") -> return 8
            reversed.contains("nine") -> return 9
            else -> updated
        }
    }
    throw IndexOutOfBoundsException("No digit found")
}
