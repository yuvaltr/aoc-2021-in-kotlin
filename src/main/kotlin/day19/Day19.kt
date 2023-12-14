package day19

import readInput

data class Coord(
    var x: Int,
    var y: Int,
    var z: Int
)

fun readDetectionCoordinates(lines: List<String>): List<List<Coord>> {
    val ret = mutableListOf<List<Coord>>()
    var scans = mutableListOf<Coord>()

    val regex = """(-?\d+),(-?\d+),(-?\d+)""".toRegex()

    for (line in lines) {
        if (line.isBlank() || line.startsWith("--")) {
            if (scans.isNotEmpty()) {
                ret.add(scans)
            }
            scans = mutableListOf()
            continue
        }

        val ll = regex.find(line)!!.destructured.toList()
            .slice(listOf(0, 1, 2))
            .map { s -> s.toInt() }

        scans.add(Coord(ll[0], ll[1], ll[2]))
    }

    return ret
}

fun rotate(coord: Coord, rotateTo: Coord, rotateBy: Int): Coord {

    return Coord(0, 0, 0)
}

/// coordinates in 3D can be taken relative to 6 axial directions, each rotated along its axis in 4 ways
fun getRotations(scans: List<Coord>): List<List<Coord>> {
    val rotations = mutableListOf<List<Coord>>()

    return rotations
}

fun isOverlapping(rotation: List<Coord>, overlapped: List<Coord>): Boolean {
    return true
}

fun findOverlaps(coordinates: List<List<Coord>>): List<List<Coord>> {
    val foundOverlaps = mutableListOf<List<Coord>>()
    val stillLooking = coordinates.toMutableList()
    foundOverlaps.add(stillLooking.removeAt(0))

    while (stillLooking.isNotEmpty()) {
        var found = false
        val stillLookingCopy = stillLooking.toMutableList()
        for (it in stillLookingCopy) {
            for (rotation in getRotations(it)) {
                for (overlapped in foundOverlaps) {
                    if (isOverlapping(rotation, overlapped)) {
                        foundOverlaps.add(rotation)
                        stillLooking.remove(it) // not removing from the list currently iterating over
                        found = true
                    }
                }
                if (found) {
                    break
                }
            }
        }

        check(found)
    }

    check(coordinates.size == foundOverlaps.size)
    return foundOverlaps
}

fun followInstructions(input: List<String>): Int {
    val coordinates = readDetectionCoordinates(input)
    val shifted = findOverlaps(coordinates)
    //return countBeacons(shifted)
    return 0
}


fun main() {
    val testInput = readInput("day19/test.txt")
    //val input = readInput("day19/input.txt")

    println("test part1 = ${followInstructions(testInput)}")
    //println("part1 = ${followInstructions(input)}")

    //println("test part2 = ${followInstructions(testInput)}")
    //println("part2 = ${followInstructions(input)}")

    return
}
