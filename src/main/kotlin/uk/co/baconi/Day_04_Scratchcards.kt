package uk.co.baconi

// https://adventofcode.com/2023/day/4
fun main() {
    dayFourPartOne()
    dayFourPartTwo()
}

private val dayThreeExample = listOf(
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", // 4
    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", // 2
    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", // 2
    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", // 1
    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", // 0
    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"  // 0
)

fun dayFourPartOne() {

    println()
    println("--- Part One --- ")

    val exampleAnswer = dayThreeExample
        .parseScratchcardStack()
        .calculateScratchcardMatches()
        .calculateScratchcardScores()
        .sum()

    println("Example answer: $exampleAnswer")

    val actualAnswer = getScratchcards()
        .parseScratchcardStack()
        .calculateScratchcardMatches()
        .calculateScratchcardScores()
        .sum()

    println("Actual answer: $actualAnswer")
}


fun dayFourPartTwo() {

    println()
    println("--- Part Two --- ")

    val exampleStack = dayThreeExample
        .parseScratchcardStack()

    val exampleAnswer = exampleStack
        .calculateScratchcardMatches()
        .calculateWinningMatches()
        .count() + exampleStack.size

    println("Example answer: $exampleAnswer")

    val actualStack = getScratchcards()
        .parseScratchcardStack()

    val actualAnswer = actualStack
        .calculateScratchcardMatches()
        .calculateWinningMatches()
        .count() + actualStack.size

    println("Actual answer: $actualAnswer")
}

fun getScratchcards(): List<String> = AdventOfCode2023.readResourceByLines("/day_04.txt")

data class Scratchcard(val index: Int, val winners: List<Int>, val numbers: List<Int>)

fun List<String>.parseScratchcardStack(): List<Scratchcard> = map { line ->

    val (card, game) = line.split(':')
    val (_, index) = card.split(' ').filter(String::isNotBlank)
    val (winners, numbers) = game.split('|').map { it.split(' ').filter(String::isNotBlank).map(String::toInt) }

    Scratchcard(index.toInt(), winners, numbers)
}

fun List<Scratchcard>.calculateScratchcardMatches(): Map<Scratchcard, Int> = associate { card ->

    val count = card.winners.fold(0) { counter, current ->
        if (card.numbers.contains(current)) {
            counter + 1
        } else {
            counter
        }
    }

    card to count
}

fun Map<Scratchcard, Int>.calculateScratchcardScores(): List<Int> = map { (_, count) ->

    when (count) {
        0 -> 0
        else -> (1..count).reduce { accumulated, _ ->
            accumulated * 2
        }
    }
}

fun Map<Scratchcard, Int>.calculateWinningMatches(): List<Int> = flatMap { (card, count) ->
    reduceWinningScratchcard(card, count)
}

fun Map<Scratchcard, Int>.reduceWinningScratchcard(card: Scratchcard, count: Int): List<Int> {

    val index = card.index + 1

    return when (count) {
        0 -> emptyList()
        else -> (index..<index + count).fold(listOf()) { accumulated, current ->
            val next = this.entries.find { it.key.index == current }
            if (next != null) {
                accumulated + current + reduceWinningScratchcard(next.key, next.value)
            } else {
                accumulated
            }
        }
    }
}