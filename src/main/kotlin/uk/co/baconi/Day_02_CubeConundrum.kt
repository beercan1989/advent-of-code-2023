package uk.co.baconi

data class Game(val id: Int, val draws: List<GameSet>)
data class GameSet(val reds: Int, val greens: Int, val blues: Int)

// https://adventofcode.com/2023/day/2
fun main() {
    dayTwoPartOne()
    dayTwoPartTwo()
}

private val dayTwoExample = listOf(
    "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
    "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
    "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
    "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
    "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
)

fun dayTwoPartOne() {

    println()
    println("--- Part One --- ")

    val exampleAnswer = dayTwoExample
        .parseGame()
        .filterPossible(reds = 12, greens = 13, blues = 14)
        .sumOf { game -> game.id }

    println("Example answer: $exampleAnswer")

    val answer = getGameRecords()
        .parseGame()
        .filterPossible(reds = 12, greens = 13, blues = 14)
        .sumOf { game -> game.id }

    println("Answer: $answer")
}

fun dayTwoPartTwo() {

    println()
    println("--- Part Two --- ")

    val exampleAnswer = dayTwoExample
        .parseGame()
        .identifyMinimumRequiredCubes()
        .sumOf { gameSet -> gameSet.reds * gameSet.greens * gameSet.blues }

    println("Example answer: $exampleAnswer")

    val answer = getGameRecords()
        .parseGame()
        .identifyMinimumRequiredCubes()
        .sumOf { gameSet -> gameSet.reds * gameSet.greens * gameSet.blues }

    println("Answer: $answer")
}

fun getGameRecords(): List<String> = AdventOfCode2023.readResourceByLines("/day_02.txt")

fun List<String>.parseGame(): List<Game> = map { line ->

    val (prefix, sets) = line.split(":")
    val (_, id) = prefix.split(" ")

    val draws = sets.split(";").map { draw ->

        draw.split(",").map(String::trim).fold(GameSet(0, 0, 0)) { accumulated: GameSet, pair: String ->

            val (count, colour) = pair.split(" ")
            when (colour) {
                "red" -> accumulated.copy(reds = count.toInt())
                "green" -> accumulated.copy(greens = count.toInt())
                "blue" -> accumulated.copy(blues = count.toInt())
                else -> accumulated
            }
        }
    }

    Game(id.toInt(), draws)
}

fun List<Game>.filterPossible(reds: Int, greens: Int, blues: Int): List<Game> = filterNot { game ->
    game.draws.any { set ->
        set.reds > reds || set.greens > greens || set.blues > blues
    }
}

fun List<Game>.identifyMinimumRequiredCubes(): List<GameSet> = map { game ->
    game.draws.fold(GameSet(0, 0, 0)) { accumulated: GameSet, current: GameSet ->
        var result = accumulated
        if (accumulated.reds < current.reds) result = result.copy(reds = current.reds)
        if (accumulated.greens < current.greens) result = result.copy(greens = current.greens)
        if (accumulated.blues < current.blues) result = result.copy(blues = current.blues)
        result
    }
}
