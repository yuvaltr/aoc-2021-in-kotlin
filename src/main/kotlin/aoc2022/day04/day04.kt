package aoc2022.day04

import readInput

fun readRanges(list: List<String>): List<Pair<IntRange, IntRange>> {
    val regex = """(\s*)(\d+)(\s*)-(\s*)(\d+)(\s*),(\s*)(\d+)(\s*)-(\s*)(\d+)(\s*)""".toRegex()

    return list.map {
        val coords = regex.find(it)!!.destructured.toList().slice(listOf(1, 4, 7, 10))
            .map { it.toIntOrNull() }
        check(coords.size == 4)
        Pair(IntRange(coords[0]!!, coords[1]!!), IntRange(coords[2]!!, coords[3]!!))
    }
}


fun part1() {
    val input = readInput("aoc2022/day04/input.txt")
    val ranges = readRanges(input)
    val res = ranges.count {
        it.first.contains(it.second.first) && it.first.contains(it.second.last)
                || it.second.contains(it.first.first) && it.second.contains(it.first.last)
    }
    println("part1 = $res")
}

fun part2() {
    val input = readInput("aoc2022/day04/input.txt")
    val ranges = readRanges(input)
    val res = ranges.count {
        it.first.contains(it.second.first) || it.first.contains(it.second.last)
                || it.second.contains(it.first.first) || it.second.contains(it.first.last)
    }
    println("part2 = $res")
}

fun main() {
    part1()
    part2()
}