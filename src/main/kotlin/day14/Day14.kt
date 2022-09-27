package day14

import readInput


fun followInstructions(input: List<String>) {
    var polymer = input[0].toCharArray()

    val instructions = input.subList(2, input.size)
        .map {
            val regex = """(\w)(\w)(\s*)->(\s*)(\w)""".toRegex()
            regex.find(it)!!.destructured.toList()
                .slice(listOf(0, 1, 4))
                .map { s -> s[0] }}
        .associate { Pair(it[0], it[1]) to it[2] }

    repeat(10) {
        val expanded = polymer.joinToString(" ").toCharArray()
        (1 until expanded.lastIndex step 2).forEach { i ->
            val k = Pair(expanded[i-1], expanded[i+1])
            if (instructions.containsKey(k)) {
                expanded[i] = instructions[k]!!
            }
        }

        polymer = expanded.toList().filter { it != ' '}.toCharArray()

    }

    val countByElement = polymer.toList().groupingBy { it }.eachCount()

    val maxByCount = countByElement.maxByOrNull { it.value }?.value!!
    val minByValue = countByElement.minByOrNull { it.value }?.value!!

    println("$maxByCount - $minByValue = ${maxByCount-minByValue}")
}

fun part1(input: List<String>): Int {
    followInstructions(input)
    return 0
}


fun part2(input: List<String>): Int {
    return 0
}


fun main() {
    val testInput = readInput("day14/test.txt")

    println("test part1 = ${part1(testInput)}")
    //println("test part2 = ${part2(testInput)}")

    val input = readInput("day14/input.txt")

    println("part1 = ${part1(input)}")
    //println("part2 = ${part2(input)}")

    return
}
