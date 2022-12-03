package aoc2022.day01

import readInput

fun <T> List<T>.splitBy(delimiter: T): List<List<T>> {
    return this
        .flatMapIndexed { index, x ->
            when {
                index == 0 || index == this.lastIndex -> listOf(index)
                x == delimiter -> listOf(index - 1, index + 1)
                else -> emptyList()
            }
        }
        .windowed(size = 2, step = 2) { (from, to) -> this.slice(from..to) }
}

fun main() {
    val numbers = readInput("aoc2022/day01/input.txt")
        .map { it.toIntOrNull() ?: 0 }
        .splitBy(0)

    val sums = numbers.map { list -> list.sumOf { it } }

    val max = sums.maxOf { it }
    println("part1 = $max")

    val maxThree = sums.sortedDescending().take(3).sumOf { it }
    println("part2 = $maxThree")
}
