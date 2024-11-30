package aoc2021.day07

import readInput
import java.lang.Math.abs
import java.util.*

fun part1(displacements: List<Int>): Int {
    val totalFuel = HashMap<Int, Int>()
    var minFuel = Int.MAX_VALUE

    displacements.forEach { coord ->
        if (!totalFuel.contains(coord)) {
            totalFuel[coord] = displacements.map { abs(it - coord) }.reduce { acc, i -> acc + i }
            if (totalFuel[coord]!! < minFuel)
                minFuel = totalFuel[coord]!!
        }
    }

    return minFuel
}

fun part2(displacements: List<Int>): Int {
    val totalFuel = HashMap<Int, Int>()
    var minFuel = Int.MAX_VALUE

    val a = displacements.minOrNull()!!
    val b = displacements.maxOrNull()!!

    (a..b).forEach { coord ->
        if (!totalFuel.contains(coord)) {
            totalFuel[coord] = displacements.map { val d = abs(it - coord); ((d+1)*d)/2 }.reduce { acc, i -> acc + i }
            if (totalFuel[coord]!! < minFuel)
                minFuel = totalFuel[coord]!!
        }
    }

    return minFuel
}

fun main() {

    val testInput = readInput("aoc2021/day07/input.txt").first().split(",").map { it.toInt() }

    println("part1 = ${part1(testInput)}")
    println("part2 = ${part2(testInput)}")

    return
}
