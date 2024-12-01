package aoc2024.day01

import readInput
import kotlin.math.abs

fun main() {
    val numbers = readInput("aoc2024/day01/input.txt")
        .map { it.split(Regex("\\s+")) }
        .map { it.map { it.toInt() } }

    val left = numbers.map { it.first() }.sorted()
    val right = numbers.map { it.last() }.sorted()

    val distances = left.zip(right) { a, b -> abs(a - b)}

    val sums = distances.sum()

    println("part1 = $sums")

    val rightFrequency = mutableMapOf<Int, Int>()

    right.forEach {
        if (rightFrequency.contains(it))
            rightFrequency[it] = rightFrequency[it]!! + 1
        else
            rightFrequency[it] = 1
    }

    val similarityScore = left.map {
        if (rightFrequency.contains(it))
            it * rightFrequency[it]!!
        else
            0
    }.sum()

    println("part2 = $similarityScore")
}
