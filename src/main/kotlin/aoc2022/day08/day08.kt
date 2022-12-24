package aoc2022.day08

import common.readMatrix
import readInput


fun part1() {
    fun findVisible(inputFilename: String) {
                val input = readInput(inputFilename)
                val heights = readMatrix(input)

        var visible = 0

        heights.rowIndices().forEach { i ->
            val row = heights.row(i)

            heights.colIndices().forEach { j ->
                val col = heights.col(j)
                val me = heights[i, j]

                if (i == 0 || j == 0 || i == heights.rowIndices().last || j == heights.colIndices().last) {
                    ++visible
                } else if (row.take(j).all { it < me } || row.drop(j+1).all { it < me }) {
                    ++visible
                } else if (col.take(i).all { it < me} || col.drop(i+1).all { it < me}) {
                    ++visible
                }
            }
        }

        println("part1 ($inputFilename) = $visible")
    }

    findVisible("aoc2022/day08/test.txt")
    findVisible("aoc2022/day08/input.txt")
}


fun part2() {
    fun highestScenicScore(inputFilename: String) {
        val input = readInput(inputFilename)
        val heights = readMatrix(input)

        var maxScore = 0

        heights.rowIndices().forEach { i ->
            val row = heights.row(i)

            heights.colIndices().forEach { j ->
                val col = heights.col(j)
                val me = heights[i, j]

                if (i != 0 && j != 0 && i != heights.rowIndices().last && j != heights.colIndices().last) {
                    val distUp = (i - col.take(i).indexOfLast { it >= me })
                        .run { if (this == i + 1) i else this }

                    val distDown = 1 + col.drop(i+1).indexOfFirst { it >= me }
                        .run { if (this < 0) col.size - i - 2 else this }

                    val distLeft = (j - row.take(j).indexOfLast { it >= me })
                        .run { if (this == j + 1) j else this }

                    val distRight = 1 + row.drop(j+1).indexOfFirst() { it >= me }
                        .run { if (this < 0) row.size - j - 2 else this }

                    val score = distUp * distDown * distLeft * distRight
                    maxScore = maxScore.coerceAtLeast(score)
                }
            }
        }

        println("part2 ($inputFilename) = $maxScore")
    }

    highestScenicScore("aoc2022/day08/test.txt")
    highestScenicScore("aoc2022/day08/input.txt")
}


fun main() {
    part1()
    part2()
}