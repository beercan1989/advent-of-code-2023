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

fun getCalibrationDocument(): List<String> = checkNotNull(
    AdventOfCode2023::class.java.getResource("/day_01.txt")?.toURI()?.let(::File)?.readLines()
)

fun List<String>.extractFirstAndLastDigit(): List<Int> = map { line ->
    val first = line.first(Char::isDigit)
    val last = line.last(Char::isDigit)
    "$first$last".toInt()
}

fun  List<String>.replaceDigitWordsWithDigits(): List<String> = map { line ->
    line.fold("") { accumulated, current ->
        if(current.isDigit()) {
            accumulated + current
        } else {
            (accumulated + current)
                .replace("one", "1")
                .replace("two", "2")
                .replace("three", "3")
                .replace("four", "4")
                .replace("five", "5")
                .replace("six", "6")
                .replace("seven", "7")
                .replace("eight", "8")
                .replace("nine", "9")
        }
    }
}
