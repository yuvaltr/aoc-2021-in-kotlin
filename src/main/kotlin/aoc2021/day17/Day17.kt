package aoc2021.day17

import common.Coord
import kotlin.math.abs

data class Area(
    val x: IntRange,
    val y: IntRange
)

fun part1(target: Area): Int {
    val yy = abs(target.y.first)
    return yy*(yy-1)/2
}

fun inArea(coord: Coord, area: Area): Boolean {
    return (coord.first in area.x && coord.second in area.y)
}

fun hitTarget(vxStart: Int, vyStart: Int, target: Area): Boolean {
    var pos = Coord(0, 0)
    var vx = vxStart
    var vy = vyStart

    while (pos.first <= target.x.last && pos.second >= target.y.first) {
        pos = Pair(pos.first + vx, pos.second + vy)
        if (inArea(pos, target))
            return true

        vx = if (vx > 0) vx - 1 else if (vx < 0) vx + 1 else 0
        vy -= 1
    }

    return false
}

fun part2(target: Area): Int {
    val yy = abs(target.y.first) + 1
    val possibleY = -yy..yy

    val xx = abs(target.x.last) + 1
    val possibleX = 1..xx

    var count = 0
    possibleX.forEach { vx ->
        possibleY.forEach { vy ->
            if (hitTarget(vx, vy, target))
                count += 1
        }
    }
    return count
}


fun main() {
    val testInput = Area(20..30, -10..-5)

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = Area(179..201, y=-109..-63)

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
