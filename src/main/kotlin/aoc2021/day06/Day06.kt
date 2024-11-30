package aoc2021.day06

import readInput
import java.util.*

fun part1(input: List<Int>, days: Int): Int {
    var state = input.toMutableList()

    for (i in 1..days) {
        val newState = state.map{ it - 1 }
        val hatched = newState.count { it < 0 }
        state = newState.map { if (it < 0) 6 else it }.toMutableList()
        state.addAll(List(hatched) { 8 })
    }

    return state.size
}

fun part2(input: List<Int>, days: Int): Long {
    var count = LongArray(size = 9)
    for (age in 0..8) {
        count[age] = input.count { it == age }.toLong()
    }

    for (i in 1..days) {
        val newState = count.toList()
        Collections.rotate(newState, -1)
        val hatched = newState[8]
        count = newState.toLongArray()
        count[6] += hatched
    }

    return count.reduce { acc, i -> acc + i }
}

fun main() {

    val testInput = readInput("aoc2021/day06/input.txt").first().split(",").map { it.toInt() }

    println("part1 = ${part1(testInput, days = 80)}")
    println("part2 = ${part2(testInput, days = 256)}")

    return
}
