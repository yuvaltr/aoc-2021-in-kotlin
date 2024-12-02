package aoc2024.day02

import readInput
import kotlin.math.abs

fun getDifferences(list: List<Int>): List<Int> {
    return list.zipWithNext { a, b -> b - a }
}

val Int.sign: Int
    get() = when {
        this > 0 -> 1
        this < 0 -> -1
        else -> 0 // Zero has no sign
    }

fun allSameSign(list: List<Int>): Boolean {
    if (list.isEmpty()) return true // Consider empty list as having the same sign
    val firstSign = list[0].sign
    return list.all { it.sign == firstSign }
}

fun isSafe(list: List<Int>): Boolean {
    val differences = getDifferences(list)
    val sameSign = allSameSign(differences)
    val nonMonotonic = differences[0].sign != 0
    val smallDifferences = differences.map { abs(it) }.max() <= 3

    return sameSign && nonMonotonic && smallDifferences
}

fun isSafeWithTolerance(list: List<Int>): Boolean {
    return list.mapIndexed { index, _ ->
        list.filterIndexed { i, _ -> i != index }
    }.any { isSafe(it) }
}

fun main() {
    val numbers = readInput("aoc2024/day02/input.txt")
        .map { it.split(Regex("\\s+")) }
        .map { it.map { it.toInt() } }

    val safe = numbers.count { isSafe(it) }

    println("part1 = $safe")

    val safe2 = numbers.count { isSafeWithTolerance(it) }

    println("part2 = $safe2")

    println("done.")
}
