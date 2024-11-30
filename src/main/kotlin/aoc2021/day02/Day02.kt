package aoc2021.day02

import readInput

fun main() {
    data class Operation(
        val direction: String,
        val amount: Int
    )

    fun part1(input: List<Operation>): Int {
        var x = 0
        var y = 0
        input.forEach {
            when (it.direction) {
                "forward" -> x += it.amount
                "down" -> y += it.amount
                "up" -> y -= it.amount
            }
        }
        return x * y
    }

    fun part2(input: List<Operation>): Int {
        var x = 0
        var y = 0
        var aim = 0
        input.forEach {
            when (it.direction) {
                "forward" -> {
                    x += it.amount
                    y += aim * it.amount
                }
                "down" -> aim += it.amount
                "up" -> aim -= it.amount
            }
        }
        return x * y
    }


    val testInput = readInput("aoc2021/day02/Day02_test.txt")
        .map { it.split(' ') }
        .map { Operation(it[0], it[1].toInt()) }

    check(testInput.size == 1000)
    println("part1 = ${part1(testInput)}")
    println("part2 = ${part2(testInput)}")
}
