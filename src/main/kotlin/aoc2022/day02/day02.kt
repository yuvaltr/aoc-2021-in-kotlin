package aoc2022.day02

import readInput

fun main() {
    val outcome = IntArray<IntArray(3)>(3)

    val input = readInput("aoc2022/day02/input.txt").map {
        val moves = it.split(" ")
        check(moves.size == 2 && moves.all {c -> c.length == 1 } )
        Pair(moves.first()[0] - 'A', moves.last()[0] - 'X')
    }


    return
}
