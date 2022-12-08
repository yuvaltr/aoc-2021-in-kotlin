package aoc2022.day05

import readInput

typealias Stack = MutableList<Char>
typealias Stacks = MutableList<Stack>

data class Instruction(
    val howMany: Int,
    val from: Int,
    val to: Int
) {
    constructor(list: List<String>): this(list[0].toInt(), list[1].toInt(), list[2].toInt())
}

typealias Instructions = MutableList<Instruction>

fun readStacksAndInstructions(lines: List<String>): Pair<Stacks, Instructions> {
    val stacks: Stacks = mutableListOf()
    val instructions: Instructions = mutableListOf()
    var readStacks = true

    val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()

    for (line in lines) {
        if (line.isEmpty()) {
            readStacks = false
            continue
        }

        if (readStacks) {
            val chars = line.toCharArray().slice(1 until line.length step 4)
            if (stacks.size < chars.size) {
                stacks.addAll(MutableList(chars.size - stacks.size) { mutableListOf() })
            }

            if (chars[0] == '1')
                continue

            chars.forEachIndexed { index, c ->
                if (c != ' ')
                    stacks[index].add(c)
            }
        } else {
            val data = regex.find(line)!!.destructured.toList()
            instructions.add(Instruction(data))
        }
    }
    return Pair(stacks, instructions)
}


fun part1() {
    val input = readInput("aoc2022/day05/input.txt")
    val data = readStacksAndInstructions(input)
    val stacks = data.first
    data.second.forEach { instruction ->
        repeat(instruction.howMany) {
            stacks[instruction.to - 1].add(0, (stacks[instruction.from - 1]).removeAt(0))
        }
    }
    val chars = String(stacks.map { it.first() }.toCharArray())
    println("part1: $chars")
}

fun part2() {
    val input = readInput("aoc2022/day05/input.txt")
    val data = readStacksAndInstructions(input)
    val stacks = data.first
    data.second.forEach { instruction ->
        val tempStack: Stack = mutableListOf()
        repeat(instruction.howMany) {
            tempStack.add(0, (stacks[instruction.from - 1]).removeAt(0))
        }
        repeat(instruction.howMany) {
            stacks[instruction.to - 1].add(0, tempStack.removeAt(0))
        }
    }
    val chars = String(stacks.map { it.first() }.toCharArray())
    println("part2: $chars")

}

fun main() {
    part1()
    part2()
}