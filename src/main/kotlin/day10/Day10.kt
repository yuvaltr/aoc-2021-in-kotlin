package day10

import common.Stack
import readInput


enum class ParseStatus {
    EXPRESSION_IS_COMPLETE,
    FOUND_ILLEGAL_CHARACTER,
    EXPRESSION_NEEDS_COMPLETION
}

fun parseScopes(line: String): Pair<ParseStatus, Any?> {
    val stack = Stack()

    val opensScope = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<')

    for (c in line.toCharArray()) {
        when (c) {
            '(', '[', '{', '<' -> stack.push(c)
            ')', ']', '}', '>' -> if (stack.pop() != opensScope[c]) return Pair(ParseStatus.FOUND_ILLEGAL_CHARACTER, c)
            else -> TODO()
        }
    }

    if (stack.isEmpty())
        return Pair(ParseStatus.EXPRESSION_IS_COMPLETE, null)

    val closesScope = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    return Pair(ParseStatus.EXPRESSION_NEEDS_COMPLETION, stack.popAll().map{ closesScope[it] })
}


fun part1(input: List<String>): Int {
    var sum = 0
    input.forEach { line ->
        val (status, c) = parseScopes(line)

        if (status == ParseStatus.FOUND_ILLEGAL_CHARACTER) {
            sum += when (c as Char) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> TODO()
            }
        }
    }
    return sum
}

fun part2(input: List<String>): Long {
    val scores = input.mapNotNull { line ->
        val (status, closingScopes) = parseScopes(line)
        if (status == ParseStatus.EXPRESSION_NEEDS_COMPLETION) {
            (closingScopes as List<Char>).fold(0L) { acc, c ->
                5 * acc + when (c) {
                    ')' -> 1
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> TODO()
                }
            }
        } else
            null
    }

    val scoresLong = scores.toLongArray()
    scoresLong.sort()
    return scoresLong[scoresLong.lastIndex/2]
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
