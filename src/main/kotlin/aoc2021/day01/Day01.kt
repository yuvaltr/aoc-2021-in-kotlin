package aoc2021.day01

import readInput

fun main() {
    fun part1(input: List<Int>): Int {
        val a = input.windowed(2)
        val b = a.count{ (a, b) -> b > a }
        return b
    }

    fun part2(input: List<Int>): Int {
        val a = input.windowed(4)
        val b = a.count{ (a, b, c, d) -> d > a }
        return b
    }


    val testInput = readInput("aoc2021/day01/Day01_test.txt")
        .map { it.toInt() }

    check(testInput.size == 2000)
    val res = part1(testInput)
    println("part1 = ${res}")

    val res2 = part2(testInput)
    println("part2 = ${res2}")

}
