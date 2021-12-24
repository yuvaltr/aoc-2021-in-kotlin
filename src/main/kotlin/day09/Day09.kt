package day09

import readInput

typealias Point = Pair<Int, Int>

data class Size(
    val rows: Int,
    val columns: Int
)

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class BoolMatrix {
    var values: Array<BooleanArray>? = null

    constructor(size: Size) {
        values = Array(size.rows) { BooleanArray(size.columns) }
    }

    fun size(): Size {
        return Size(values?.size ?: 0, values?.first()?.size ?: 0)
    }

    fun at(p: Point, direction: Direction? = null): Boolean? {
        val location = when (direction) {
            Direction.UP -> Point(p.first - 1, p.second)
            Direction.DOWN -> Point(p.first + 1, p.second)
            Direction.LEFT -> Point(p.first, p.second - 1)
            Direction.RIGHT -> Point(p.first, p.second + 1)
            else -> p
        }

        if (location.first < 0 || size().rows - 1 < location.first
            || location.second < 0 || size().columns - 1 < location.second)
            return null

        return values!![location.first][location.second]
    }

    fun countTrue(): Int {
        return values!!.fold(0) { acc: Int, booleans: BooleanArray -> acc + booleans.count { it } }
    }
}

class IntMatrix {
    var values: Array<IntArray>? = null

    constructor(size: Size) {
        values = Array(size.rows) { IntArray(size.columns) }
    }

    constructor(v: Array<IntArray>) {
        values = v
    }

    fun size(): Size {
        return Size(values?.size ?: 0, values?.first()?.size ?: 0)
    }

    fun at(p: Point, direction: Direction? = null): Int? {
        val location = when (direction) {
            Direction.UP -> Point(p.first - 1, p.second)
            Direction.DOWN -> Point(p.first + 1, p.second)
            Direction.LEFT -> Point(p.first, p.second - 1)
            Direction.RIGHT -> Point(p.first, p.second + 1)
            else -> p
        }

        if (location.first < 0 || size().rows - 1 < location.first
            || location.second < 0 || size().columns - 1 < location.second)
            return null

        return values!![location.first][location.second]
    }
}

fun List<String>.toIntMatrix(): IntMatrix {
    val matrix = this.map {
        it.toList().map { char -> char.toString() }.map { digit -> digit.toInt() }.toIntArray()
    }.toTypedArray()

    check(matrix.all { it.size == matrix.first().size } )

    return IntMatrix(matrix)
}



fun findLocalMinimas(mat: IntMatrix): List<Point> {
    val minimas = mutableListOf<Point>()

    mat.values?.forEachIndexed { m, ints ->
        ints.forEachIndexed { n, value ->
            if (Direction.values().all { (mat.at(Point(m, n), it) ?: Int.MAX_VALUE) > value })
                minimas.add(Point(m,n))
        }
    }

    return minimas
}

fun findBasinSize(heights: IntMatrix, location: Point): Int {
    val visited = BoolMatrix(heights.size())

    fun markBasin(p: Point) {
        if (heights.at(p) == null)
            return

        // requirement
        if (heights.at(p) == 9)
            return

        if (visited.values!![p.first][p.second])
            return

        // for each 4-adjacent location, this location is in the basin if it is lower than all 4-adjacent non-visited points
        val inBasin = Direction.values().all { dir ->
            visited.at(p, dir) != false || (visited.at(p, dir) == false && (heights.at(p, dir) ?: Int.MAX_VALUE) >= (heights.at(p))!!) }

        if (inBasin) {
            visited.values!![p.first][p.second] = true
            markBasin(Point(p.first + 1, p.second))
            markBasin(Point(p.first - 1, p.second))
            markBasin(Point(p.first, p.second + 1))
            markBasin(Point(p.first, p.second - 1))
        }
    }

    markBasin(location)
    return visited.countTrue()
}

fun part1(input: IntMatrix): Int {
    return findLocalMinimas(input).count()
}

fun part2(input: IntMatrix): Int {
    val basinSizes = findLocalMinimas(input).map { findBasinSize(input, it) }.toMutableList()
    require(basinSizes.size >= 3)

    basinSizes.sort()
    return basinSizes
        .takeLast(3)
        .fold(1) { acc, i -> acc * i }
}

fun main() {
    val testInput = readInput("day09/test.txt").toIntMatrix()

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day09/input.txt").toIntMatrix()

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
