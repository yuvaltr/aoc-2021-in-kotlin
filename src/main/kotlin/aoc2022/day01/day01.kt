package aoc2022.day01

import readInput


fun main() {
    val input = readInput("aoc2022/day01/input.txt").map { it.toIntOrNull() ?: 0 }

    val result = input
        .flatMapIndexed { index, x ->
            when {
                index == 0 || index == input.lastIndex -> listOf(index)
                x == 0 -> listOf(index - 1, index + 1)
                else -> emptyList()
            }
        }
        .windowed(size = 2, step = 2) { (from, to) -> input.slice(from..to) }

    val sums = result.map { list -> list.sumOf { it } }

    val max = sums.maxOf { it }

    val maxThree = sums.sortedDescending().take(3).sumOf { it }

    return
}
