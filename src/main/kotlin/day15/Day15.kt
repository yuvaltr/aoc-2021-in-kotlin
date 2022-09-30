package day15

import readInput

typealias Coord = Pair<Int, Int>
typealias IntMat = Array<IntArray>

fun readMatrix(input: List<String>): IntMat {
    return input
        .map {  line ->
            line.toCharArray().map { (it - '0') }.toIntArray()
        }
        .toTypedArray()
}

fun shortPath(weights: Array<IntArray>, start: Coord, end: Coord): Int {
    // Dijkstra's

    // set all nodes as "unvisited"
    val unvisitedNodes = hashSetOf<Coord>().apply {
        weights.indices.forEach { i ->
            weights[i].indices.forEach { j ->
                this.add(Coord(i, j))
            }
        }
    }

    // below, when we'll look for the next unvisited node to visit,
    // only consider nodes to which a path was already found (otherwise there's no path to find)
    val unvisitedNodesWithTentativeDistance = hashSetOf<Coord>()

    fun markVisited(coord: Coord) {
        unvisitedNodes.remove(coord)
        unvisitedNodesWithTentativeDistance.remove(coord)
    }

    // initialize all tentative distances to INF
    val tentativeDistances = Array(weights.size) { IntArray(weights[0].size) { Int.MAX_VALUE } }.apply {
        this[start.first][start.second] = 0
        unvisitedNodesWithTentativeDistance.add(start)
    }

    fun unvisitedNeighborsOf(coord: Coord): List<Coord> {
        return mutableListOf<Coord>().apply {
            (coord.first-1 .. coord.first+1).forEach { i ->
                (coord.second - 1..coord.second + 1).forEach { j ->
                    if (i in weights.indices && j in weights[i].indices
                        && unvisitedNodes.contains(Coord(i, j)) // coord inside the matrix
                        && (i == coord.first || j == coord.second) // 4-neighbor
                        && Coord(i, j) != coord) { // not the self
                        this.add(Coord(i, j))
                    }
                }
            }
        }
    }

    fun unvisitedWithSmallestTentativeDistance(): Coord? {
        var coord = unvisitedNodes.firstOrNull()
            ?: return null

        var minimalTentativeDistance = Int.MAX_VALUE

        unvisitedNodesWithTentativeDistance.forEach {
            if (tentativeDistances[it.first][it.second] < minimalTentativeDistance) {
                minimalTentativeDistance = tentativeDistances[it.first][it.second]
                coord = it
            }
        }

        // if there aren't unvisited nodes with a non-INF distance, there's no path
        return if (minimalTentativeDistance < Int.MAX_VALUE)
            coord
        else
            null
    }

    fun visitNeighborsOf(coord: Coord) {
        unvisitedNeighborsOf(coord).forEach {
            val newTentativeDistance = tentativeDistances[coord.first][coord.second] + weights[it.first][it.second]
            tentativeDistances[it.first][it.second] = newTentativeDistance.coerceAtMost(tentativeDistances[it.first][it.second])
            unvisitedNodesWithTentativeDistance.add(it)
        }
    }

    while (unvisitedNodes.contains(end)) {
        val nextNode = unvisitedWithSmallestTentativeDistance()
            ?: return Int.MAX_VALUE

        visitNeighborsOf(nextNode)

        markVisited(nextNode)
    }

    return tentativeDistances[end.first][end.second]
}

fun part1(input: List<String>): Int {
    val risks = readMatrix(input)
    return shortPath(risks, Pair(0, 0), Pair(risks.lastIndex, risks[0].lastIndex))
}

fun part2(input: List<String>): Int {
    val risksPartial = readMatrix(input)

    check(risksPartial.all { it.size == risksPartial.size })
    val n = risksPartial.size

    val risks = Array(5 * risksPartial.size) { IntArray(5 * risksPartial[0].size)}.apply {
        this.indices.forEach { i ->
            this[i].indices.forEach { j ->
                val originalValue = risksPartial[i % n][j % n]
                val addition = (i / n) + (j / n)
                this[i][j] = originalValue + addition
                if (this[i][j] > 9)
                    this[i][j] -= 9
            }
        }
    }

    return shortPath(risks, Pair(0, 0), Pair(risks.lastIndex, risks[0].lastIndex))
}

fun main() {
    val testInput = readInput("day15/test.txt")

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day15/input.txt")

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
