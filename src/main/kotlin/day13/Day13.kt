package day13

import readInput


fun followInstructions(input: List<String>) {
    // read input instructions
    var reachedInstructions = false
    val coords = mutableListOf<Pair<Int, Int>>()
    val instructions = mutableListOf<Pair<Char, Int>>()
    input.forEach { line ->
        if (!reachedInstructions) {
            if (line.isBlank()) {
                reachedInstructions = true
            } else {
                val regex = """(\s*)(\d+)(\s*),(\s*)(\d+)(\s*)""".toRegex()

                coords.add(
                    regex.find(line)!!.destructured.toList()
                        .slice(listOf(1, 4))
                        .map { s -> s.toInt() }
                        .let {Pair(it[0], it[1])}
                )
            }
        } else {
            val regex = """fold along (\w*)=(\d+)""".toRegex()

            instructions.add(
                regex.find(line)!!.destructured.toList()
                    .slice(listOf(0, 1))
                    .let {Pair(it[0][0], it[1].toInt())}
            )
        }
    }

    // calculate paper dimensions
    var dimensions = Pair(0, 0)
    coords.forEach {
        dimensions = Pair(
            dimensions.first.coerceAtLeast(it.first + 1),
            dimensions.second.coerceAtLeast(it.second + 1)
        )
    }

    // create and initialize paper dots
    var dots = Array(dimensions.first) { BooleanArray(dimensions.second) }

    coords.forEach {
        dots[it.first][it.second] = true
    }

    // follow fold instructions
    instructions.forEach { (axis, along) ->
        val dims = Pair(dots.size, dots[0].size)
        if (axis == 'x') {
            check(along == dims.first/2) { "fold not in the middle" }
            check(dots[along].all{ it == false }) { "fold line has dots on it" }

            val folded = Array(dots.size/2) { BooleanArray(dots[0].size) }
            folded.indices.forEach { i ->
                folded[i].indices.forEach { j ->
                    folded[i][j] = dots[i][j] || dots[dims.first - 1 - i][j]
                }
            }

            dots = folded

        } else if (axis == 'y') {
            check(along == dims.second/2) { "fold not in the middle" }
            check(dots.all{ column -> column[along] == false }) { "fold line has dots on it" }

            val folded = Array(dots.size) { BooleanArray(dots[0].size/2) }
            folded.indices.forEach { i ->
                folded[i].indices.forEach { j ->
                    folded[i][j] = dots[i][j] || dots[i][dims.second - 1 - j]
                }
            }

            dots = folded
        }

        val count = dots.sumOf { col -> col.count { it } }
        println("count after folding along $axis=$along = $count")
    }

    // rotate
    val rotated = Array(dots[0].size) { BooleanArray(dots.size) }
    rotated.indices.forEach { i ->
        rotated[i].indices.forEach { j ->
            rotated[i][j] = dots[j][i]
        }
    }

    // print
    rotated.forEach { row ->
        val a = row.map { if (it) '*' else ' '}.toList().joinToString("")
        println(a)
    }
}


fun part1(input: List<String>): Int {
    followInstructions(input)
    return 0
}


fun part2(input: List<String>): Int {
    return 0
}


fun main() {
    val testInput = readInput("day13/test.txt")

    println("test part1 = ${part1(testInput)}")
    //println("test part2 = ${part2(testInput)}")

    val input = readInput("day13/input.txt")

    println("part1 = ${part1(input)}")
    //println("part2 = ${part2(input)}")

    return
}
