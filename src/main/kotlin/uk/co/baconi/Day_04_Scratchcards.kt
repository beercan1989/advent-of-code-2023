package uk.co.baconi

// https://adventofcode.com/2023/day/4
fun main() {
    dayFourPartOne()
    dayFourPartTwo()
}

private val dayThreeExample = listOf(
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
)

fun dayFourPartOne() {

    println()
    println("--- Part One --- ")

    val exampleAnswer = dayThreeExample
        .parseScratchcardStack()
        .calculateScratchcardScores()
        .sum()

    println("Example answer: $exampleAnswer")

    val calibrationAnswer = getScratchcards()
        .parseScratchcardStack()
        .calculateScratchcardScores()
        .sum()

    println("Calibration answer: $calibrationAnswer")
}


fun dayFourPartTwo() {

    println()
    println("--- Part Two --- ")

    val exampleAnswer = ""

    println("Example answer: $exampleAnswer")

    val calibrationAnswer = ""

    println("Calibration answer: $calibrationAnswer")
}

fun getScratchcards(): List<String> = AdventOfCode2023.readResourceByLines("/day_04.txt")

data class Scratchcard(val index: Int, val winners: List<Int>, val numbers: List<Int>)

fun List<String>.parseScratchcardStack(): List<Scratchcard> = map { line ->

    val (card, game) = line.split(':')
    val (_, index) = card.split(' ').filter(String::isNotBlank)
    val (winners, numbers) = game.split('|').map { it.split(' ').filter(String::isNotBlank).map(String::toInt) }

    Scratchcard(index.toInt(), winners, numbers)
}

fun List<Scratchcard>.calculateScratchcardScores(): List<Int> = map { card ->

    val count = card.winners.fold(0) { counter, current ->
        if (card.numbers.contains(current)) {
            counter + 1
        } else {
            counter
        }
    }

    when(count) {
        0 -> 0
        else -> (1..count).reduce { accumulated, _ ->
            accumulated * 2
        }
    }
}
