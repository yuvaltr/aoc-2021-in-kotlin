package aoc2022.day06

import readInput

fun uniqueWindow(message: String, windowSize: Int): Int {
    val windowed = message.toCharArray().toList().windowed(windowSize)
    val marker = windowed.indexOfFirst {
        val set = it.toSet()
        set.size == windowSize
    }
    return marker + windowSize
}

fun part1() {
    val input = readInput("aoc2022/day06/input.txt").first()
    val marker = uniqueWindow(input, 4)
    println("part1 - marker = $marker")
}

fun part2() {
    val input = readInput("aoc2022/day06/input.txt").first()
    val marker = uniqueWindow(input, 14)
    println("part2 - marker = $marker")
}

fun main() {
    part1()
    part2()
}