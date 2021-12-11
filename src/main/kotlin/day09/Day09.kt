package day09

import readInput
import java.util.*

data class Size(
    val rows: Int,
    val columns: Int
)

class BoolMatrix {
    var values: Array<BooleanArray>? = null

    constructor(size: Int) {
        values = Array(size) { BooleanArray(size) }
    }

    constructor(size: Size) {
        values = Array(size.rows) { BooleanArray(size.columns) }
    }

    constructor(v: Array<BooleanArray>) {
        values = v
    }
}

class IntMatrix {
    var values: Array<IntArray>? = null

    constructor(size: Int) {
        values = Array(size) { IntArray(size) }
    }

    constructor(size: Size) {
        values = Array(size.rows) { IntArray(size.columns) }
    }

    constructor(v: Array<IntArray>) {
        values = v
    }

    fun size(): Size {
        return Size(values?.size ?: 0, values?.first()?.size ?: 0)
    }

    fun shift(direction: Direction, maskValue: Int): IntMatrix? {
        val rows = this.values ?: return null

        var rowList = LinkedList(rows.toList())

        when (direction) {
            Direction.UP -> {
                Collections.rotate(rowList, -1)
                rowList[rowList.lastIndex] = IntArray(rowList.first().size) { maskValue }
            }
            Direction.DOWN -> {
                Collections.rotate(rowList, 1)
                rowList[0] = IntArray(rowList.first().size) { maskValue }
            }
            else -> {
                val a = rowList.map { row ->
                    val rowAsList: LinkedList<Int> = LinkedList(row.toList())
                    when (direction) {
                        Direction.RIGHT -> {
                            Collections.rotate(rowAsList, -1)
                            rowAsList[rowAsList.lastIndex] = maskValue
                        }
                        Direction.LEFT -> {
                            Collections.rotate(rowAsList, 1)
                            rowAsList[0] = maskValue
                        }
                        else -> {
                            throw Exception("Unknown shift direction")
                        }
                    }
                    rowAsList.toIntArray()
                }
                rowList = LinkedList(a)
            }
        }

        val shifted = IntMatrix(rowList.toList().toTypedArray())
        assert(shifted.values?.size == this.values?.size && shifted.values?.first()?.size == this.values?.first()?.size)

        return shifted
    }
}

fun List<String>.toIntMatrix(): IntMatrix {
    val matrix = this.map {
        it.toList().map { char -> char.toString() }.map { digit -> digit.toInt() }.toIntArray()
    }.toTypedArray()

    check(matrix.all { it.size == matrix.first().size } )

    return IntMatrix(matrix)
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

//fun findLocalMinima(mat: IntMatrix): BoolMatrix {
//    val boolMat = BoolMatrix(mat.size())
//
//    mat.values?.forEachIndexed { m, ints ->
//        ints.forEachIndexed { n, value ->
//            if (shiftedMatrix.all { it!!.values!![m][n] > value  })
//                count += value + 1
//        }
//    }
//}

fun countLocalMinima(mat: IntMatrix): Int {
    val shiftedMatrix = Direction.values().toList().map { mat.shift(it, Int.MAX_VALUE) }

    var count = 0
    mat.values?.forEachIndexed { m, ints ->
        ints.forEachIndexed { n, value ->
            if (shiftedMatrix.all { it!!.values!![m][n] > value  })
                count += value + 1
        }
    }

    return count
}


fun part1(input: IntMatrix): Int {
    return countLocalMinima(input)
}

fun part2(input: IntMatrix): Int {

    return 1
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
