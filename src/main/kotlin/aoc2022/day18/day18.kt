package aoc2022.day18

import readInput

data class Position(
    val x: Int,
    val y: Int,
    val z: Int
)


fun readPositions(input: String): HashSet<Position> {
    val regex = """(\d+),(\d+),(\d+)""".toRegex()

    return readInput(input)
        .map {
            val data = regex.find(it)!!.destructured.toList()
            check(data.size == 3)
            Position(data[0].toInt(), data[1].toInt(), data[2].toInt())
        }
        .toHashSet()
}


fun findAdjacent(position: Position, existing: HashSet<Position>): Int {
    if (existing.contains(position))
        throw IllegalStateException("set already contains position")

    var adj = 0
    if (existing.contains(Position(position.x + 1, position.y, position.z)))
        ++adj
    if (existing.contains(Position(position.x - 1, position.y, position.z)))
        ++adj
    if (existing.contains(Position(position.x, position.y + 1, position.z)))
        ++adj
    if (existing.contains(Position(position.x, position.y - 1, position.z)))
        ++adj
    if (existing.contains(Position(position.x, position.y, position.z + 1)))
        ++adj
    if (existing.contains(Position(position.x, position.y, position.z - 1)))
        ++adj

    return adj
}


fun part1() {
    fun findSurfaceArea(inputFilename: String) {
        val positions = readPositions(inputFilename)

        val copied = hashSetOf<Position>()
        var area = 0

        do {
            val e = positions.first()
            check(!copied.contains(e))

            area += 6 - 2 * findAdjacent(e, copied)
            copied.add(e)
            positions.remove(e)
        } while (positions.isNotEmpty())

        println("part1 ($inputFilename) = $area")
    }

    findSurfaceArea("aoc2022/day18/test.txt")
    findSurfaceArea("aoc2022/day18/input.txt")
}


fun part2() {
    fun findSurfaceArea(inputFilename: String) {
        val positions = readPositions(inputFilename)

        val copied = hashSetOf<Position>()
        var area = 0

        do {
            val e = positions.first()
            check(!copied.contains(e))

            area += 6 - 2 * findAdjacent(e, copied)
            copied.add(e)
            positions.remove(e)
        } while (positions.isNotEmpty())

        println("part2 ($inputFilename) = $area")
    }

    findSurfaceArea("aoc2022/day18/test.txt")
    findSurfaceArea("aoc2022/day18/input.txt")
}

fun main() {
    part1()
    part2()
}