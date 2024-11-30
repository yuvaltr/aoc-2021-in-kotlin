package day15

import readInput
import common.DijkstraShortestPath
import common.SimpleIntMat

fun readMatrix(input: List<String>): SimpleIntMat {
    return input
        .map {  line ->
            line.toCharArray().map { (it - '0') }.toIntArray()
        }
        .toTypedArray()
}


fun part1(input: List<String>): Int {
    val risks = readMatrix(input)
    return DijkstraShortestPath().get(risks, Pair(0, 0), Pair(risks.lastIndex, risks[0].lastIndex))
}

fun part2(input: List<String>): Int {
    val risksPartial = readMatrix(input)

    check(risksPartial.all { it.size == risksPartial.size })
    val n = risksPartial.size

    val risks = Array(5 * risksPartial.size) { IntArray(5 * risksPartial[0].size)}.apply {
        this.indices.forEach { i ->
            this[i].indices.forEach { j ->
                val originalValue = risksPartial[i % n][j % n]
                val addition = (i / n) + (j / n)
                this[i][j] = originalValue + addition
                if (this[i][j] > 9)
                    this[i][j] -= 9
            }
        }
    }

    return DijkstraShortestPath().get(risks, Pair(0, 0), Pair(risks.lastIndex, risks[0].lastIndex))
}

fun main() {
    val testInput = readInput("day15/test.txt")

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day15/input.txt")

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
