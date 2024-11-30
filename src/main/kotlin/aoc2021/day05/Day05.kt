package aoc2021.day05

import readInput
import kotlin.math.abs


typealias Point = Pair<Int, Int>

data class Line(
    val start: Point,
    val end: Point
) {
    fun isHorizontal(): Boolean {
        return start.second == end.second
    }

    fun isVertical(): Boolean {
        return start.first == end.first
    }

    fun isDiagonal(): Boolean {
        return (abs(end.first - start.first) == abs(end.second - start.second))
    }

    fun getLatticePoints(): List<Point> {
        return if (isHorizontal()) {
            val a = minOf(start.first, end.first)
            val b = maxOf(start.first, end.first)
            (a..b).map{Point(it, start.second)}

        } else if (isVertical()) {
            val a = minOf(start.second, end.second)
            val b = maxOf(start.second, end.second)
            (a..b).map { Point(start.first, it)}

        } else if (isDiagonal()) {
            val a = minOf(start.first, end.first)
            val b = maxOf(start.first, end.first)

            val c = minOf(start.second, end.second)
            val d = maxOf(start.second, end.second)

            val flipped = (start.first == a && start.second == d || start.first == b && start.second == c)

            val rangeX = (a..b).toList()
            val rangeY = if (flipped) (c..d).toList().reversed() else (c..d).toList()

            return rangeX.zip(rangeY) { x, y -> Point(x, y) }

        } else {
            TODO()
        }
    }
}



fun main() {
    fun part1(input: List<Line>, size: Int): Int {
        val perps = input.filter { it.isHorizontal() || it.isVertical() }

        val plane = Array(size) { IntArray(size) }

        perps.forEach { line->
            line.getLatticePoints().forEach { point ->
                plane[point.first][point.second] += 1
            }
        }

        val countMultipleHits = plane
            .map { column -> column.count {it > 1} }
            .reduce { sum, rowCount -> sum + rowCount }

        return countMultipleHits
    }

    fun part2(input: List<Line>, size: Int): Int {
        val perps = input.filter { it.isHorizontal() || it.isVertical() || it.isDiagonal() }

        val plane = Array(size) { IntArray(size) }

        perps.forEach { line->
            line.getLatticePoints().forEach { point ->
                plane[point.first][point.second] += 1
            }
        }

        val countMultipleHits = plane
            .map { column -> column.count {it > 1} }
            .reduce { sum, rowCount -> sum + rowCount }

        return countMultipleHits
    }

    val regex = """(\s*)(\d+)(\s*),(\s*)(\d+)(\s*)->(\s*)(\d+)(\s*),(\s*)(\d+)(\s*)""".toRegex()

    var minCoord = Int.MAX_VALUE
    var maxCoord = Int.MIN_VALUE

    val testInput = readInput("aoc2021/day05/input.txt")
        .map {
            val coords = regex.find(it)!!.destructured.toList().slice(listOf(1, 4, 7, 10)).map{ s -> s.toInt() }
            minCoord = minOf(minCoord, coords.minOrNull()!!)
            maxCoord = maxOf(maxCoord, coords.maxOrNull()!!)
            Line(Point(coords[0], coords[1]), Point(coords[2], coords[3]))
        }

    check(minCoord >= 0)

    println("part1 = ${part1(testInput, maxCoord + 1)}")
    println("part2 = ${part2(testInput, maxCoord + 1)}")

    return
}
