package aoc2022.day03

import readInput


fun part1() {
    val res = readInput("aoc2022/day03/input.txt")
        .map {
            val half = it.length/2
            val intersect = it.take(half).toHashSet().intersect(it.drop(half).toHashSet())
            intersect.single()
        }
        .map {
            if (it.isUpperCase())
                it - 'A' + 27
            else
                it - 'a' + 1
        }
        .sumOf { it }

    println("part1 = $res")
}

fun part2() {
    val res = readInput("aoc2022/day03/input.txt")
        .windowed(3, 3)
        .map {
            check(it.size == 3)
            val intersect = it[0].toHashSet().intersect(it[1].toHashSet()).intersect(it[2].toHashSet())
            intersect.single()
        }
        .map {
            if (it.isUpperCase())
                it - 'A' + 27
            else
                it - 'a' + 1
        }
        .sumOf { it }

    println("part2 = $res")
}

fun main() {
    part1()
    part2()
}