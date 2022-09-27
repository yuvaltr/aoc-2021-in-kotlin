package day11

import readInput


typealias IntMat = Array<IntArray>
typealias BoolMat = Array<BooleanArray>

data class EnergyLevelState(val input: List<String>) {

    private var levels: IntMat = fromInput(input)
    private var step = 0
    private var flashes = 0

    private fun fromInput(input: List<String>): IntMat {
        return input.map {
            it
                .toList()
                .map { char -> (char - '0') }
                .toIntArray()
        }.toTypedArray()
    }

    private fun singleStep() {
        step += 1
        flashes += executeStep()
    }

    fun untilStep(target: Int): Int {
        while (step < target) {
            singleStep()
        }

        return flashes
    }

    fun untilAllFlash(): Int {
        val size = levels.size * levels[0].size
        var prevFlashes = 0
        while (true) {
            singleStep()
            if (flashes - prevFlashes == size) {
                return step
            } else {
                prevFlashes = flashes
            }
        }
    }

    private fun executeStep(): Int {
        // increase all levels by 1
        increaseAll(levels)

        // high-energy will flash
        val flashed = Array(levels.size) { BooleanArray(levels[0].size) }

        var point = nextFlashing(levels, flashed)
        while (point != null) {
            flashed[point.first][point.second] = true
            increaseAround(point, levels)

            point = nextFlashing(levels, flashed)
        }

        // flashed will reset their energy
        resetFlashed(levels, flashed)

        val flashes = countFlashes(flashed)
        return flashes
    }

    companion object {
        private fun increaseAll(levels: IntMat) {
            levels.indices.forEach { i ->
                levels[i].indices.forEach { j ->
                    levels[i][j] += 1
                }
            }
        }

        private fun increaseAround(point: Pair<Int, Int>, levels: IntMat) {
            (point.first - 1..point.first + 1).forEach { i ->
                if (i in levels.indices) {
                    (point.second - 1..point.second + 1).forEach { j ->
                        if (j in levels[0].indices) {
                            levels[i][j] += 1
                        }
                    }
                }
            }
        }

        private fun countFlashes(flashed: BoolMat) =
            flashed.sumOf { row -> row.count { it } }

        private fun nextFlashing(levels: IntMat, flashed: BoolMat): Pair<Int, Int>? {
            levels.indices.forEach { i ->
                levels[i].indices.forEach { j ->
                    if (levels[i][j] > 9 && !flashed[i][j])
                        return Pair(i, j)
                }
            }

            return null
        }

        private fun resetFlashed(levels: IntMat, flashed: BoolMat) {
            levels.indices.forEach { i ->
                levels[i].indices.forEach { j ->
                    if (flashed[i][j])
                        levels[i][j] = 0
                }
            }
        }
    }
}


fun part1(input: List<String>): Int {
    val state = EnergyLevelState(input)
    return state.untilStep(100)
}


fun part2(input: List<String>): Int {
    val state = EnergyLevelState(input)
    return state.untilAllFlash()
}


fun main() {
    val testInput = readInput("day11/test.txt")

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day11/input.txt")

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
