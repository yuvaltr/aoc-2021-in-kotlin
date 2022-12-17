package aoc2022.day09

import readInput
import kotlin.math.abs

data class Coord(
    var x: Int,
    var y: Int
)

fun Coord.applyStep(direction: Char): Coord {
    return when (direction) {
        'U' -> Coord(this.x, this.y + 1)
        'D' -> Coord(this.x, this.y - 1)
        'L' -> Coord(this.x - 1, this.y)
        'R' -> Coord(this.x + 1, this.y)
        else -> throw IllegalArgumentException()
    }
}

fun Coord.normalize() {
    fun normalize(i: Int): Int {
        return if (i > 0)
            1
        else if (i < 0)
            -1
        else
            0
    }

    this.x = normalize(this.x)
    this.y = normalize(this.y)
}

fun Coord.pullToward(head: Coord): Coord {
    val diff = Coord(head.x - this.x, head.y - this.y)

    if (abs(diff.x) <= 1 && abs(diff.y) <= 1)
        return Coord(this.x, this.y)

    diff.normalize()

    return Coord(this.x + diff.x, this.y + diff.y)
}

fun part1(filename: String) {
    var head = Coord(0, 0)
    var tail = Coord(0, 0)
    val visited = mutableSetOf(tail)

    readInput("aoc2022/day09/${filename}.txt")
        .forEach { instruction ->
            val steps = instruction.drop(1).trim().toInt()
            val direction = instruction.first()
            repeat(steps) {
                head = head.applyStep(direction)
                tail = tail.pullToward(head)
                visited.add(tail)
            }
        }

    println("part1 ($filename): tail visited ${visited.size} positions")
}

fun part2(filename: String, nKnots: Int) {
    val knots = Array<Coord>(nKnots) { Coord(0, 0) }
    val visited = mutableSetOf(Coord(0,0))

    readInput("aoc2022/day09/${filename}.txt")
        .forEach { instruction ->
            val steps = instruction.drop(1).trim().toInt()
            val direction = instruction.first()
            repeat(steps) {
                knots[0] = knots[0].applyStep(direction)
                (1 until nKnots).forEach {
                    knots[it] = knots[it].pullToward(knots[it - 1])
                }
                visited.add(knots.last())
            }
        }

    println("part2 ($filename): tail visited ${visited.size} positions")
}

fun main() {
    part1("test")
    part1("input")
    part2("test", 10)
    part2("input", 10)
}