package aoc2022.day02

import readInput

fun main() {
    val outcome1 = mapOf(
        Pair('A', 'X') to 1 + 3,
        Pair('B', 'X') to 1 + 0,
        Pair('C', 'X') to 1 + 6,
        Pair('A', 'Y') to 2 + 6,
        Pair('B', 'Y') to 2 + 3,
        Pair('C', 'Y') to 2 + 0,
        Pair('A', 'Z') to 3 + 0,
        Pair('B', 'Z') to 3 + 6,
        Pair('C', 'Z') to 3 + 3
    )

    val res = readInput("aoc2022/day02/input.txt")
        .map {
            val moves = it.split(" ")
            check(moves.size == 2 && moves.all {c -> c.length == 1 } )

            val play = Pair(moves.first()[0], moves.last()[0])
            check(outcome1.containsKey(play))
            outcome1[play]!!
        }
        .sumOf { it }

    println("part1 = $res")

    val outcome2 = mapOf(
        Pair('A', 'X') to 3 + 0,
        Pair('B', 'X') to 1 + 0,
        Pair('C', 'X') to 2 + 0,
        Pair('A', 'Y') to 1 + 3,
        Pair('B', 'Y') to 2 + 3,
        Pair('C', 'Y') to 3 + 3,
        Pair('A', 'Z') to 2 + 6,
        Pair('B', 'Z') to 3 + 6,
        Pair('C', 'Z') to 1 + 6
    )

    val res2 = readInput("aoc2022/day02/input.txt")
        .map {
            val moves = it.split(" ")
            check(moves.size == 2 && moves.all {c -> c.length == 1 } )

            val play = Pair(moves.first()[0], moves.last()[0])
            check(outcome2.containsKey(play))
            outcome2[play]!!
        }
        .sumOf { it }

    println("part2 = $res2")


    return
}
