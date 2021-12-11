package day10

import readInput

class Stack {
    private val elements: MutableList<Any> = mutableListOf()

    private fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    fun push(item: Any) = elements.add(item)

    fun pop() : Any? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }

    fun peek() : Any? = elements.lastOrNull()

    override fun toString(): String = elements.toString()
}

fun firstIllegalToken(line: String): Char? {
    val stack = Stack()

    val matches = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<')

    for (c in line.toCharArray()) {
        when (c) {
            '(', '[', '{', '<' -> stack.push(c)
            ')', ']', '}', '>' -> if (stack.pop() != matches[c]) return c
            else -> TODO()
        }
    }

    return null
}

fun completeTokens(line: String): String? {
    if (firstIllegalToken(line) != null)
        return null

    TODO()
}

fun part1(input: List<String>): Int {
    var sum = 0
    input.forEach { line ->
        sum += when (firstIllegalToken(line)) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }
    return sum
}

fun part2(input: List<String>): Int {

    return 1
}

fun main() {
    val testInput = readInput("day10/test.txt")

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day10/input.txt")

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
