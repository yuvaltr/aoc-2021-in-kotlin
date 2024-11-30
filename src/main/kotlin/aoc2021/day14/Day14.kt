package aoc2021.day14

import readInput


typealias AdjElem = Pair<Char, Char>



fun readInstructions(input: List<String>): Map<AdjElem, Char> {
    return input
        .map {
            val regex = """(\w)(\w)(\s*)->(\s*)(\w)""".toRegex()
            regex.find(it)!!.destructured.toList()
                .slice(listOf(0, 1, 4))
                .map { s -> s[0] }}
        .associate { Pair(it[0], it[1]) to it[2] }
}

fun followInstructions(input: List<String>): Int {
    var polymer = input[0].toCharArray()

    val instructions = readInstructions(input.subList(2, input.size))

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

    println("length = ${polymer.size}")

    val countByElement = polymer.toList().groupingBy { it }.eachCount()

    val maxByCount = countByElement.maxByOrNull { it.value }?.value!!
    val minByCount = countByElement.minByOrNull { it.value }?.value!!

    println("$maxByCount - $minByCount = ${maxByCount-minByCount}")

    return maxByCount - minByCount
}

fun <K> HashMap<K, Long>.setOrAdd(key: K, v: Long) {
    if (this.containsKey(key))
        this[key] = this[key]!! + v
    else
        this[key] = v
}

fun followInstructionsV2(input: List<String>): Long {

    fun countAdj(template: CharArray) =
        hashMapOf<AdjElem, Long>().apply {
            template.toList().zipWithNext().forEach {
                this.setOrAdd(it, 1)
            }
        }

    val initialTemplate = input[0].toCharArray()
    var pairCount = countAdj(initialTemplate)

    val instructions = readInstructions(input.subList(2, input.size))

    repeat(40) {
        val newCount = hashMapOf<AdjElem, Long>()

        pairCount.forEach { (k, count) ->
            if (instructions.containsKey(k)) {
                val v = instructions[k]!!
                newCount.setOrAdd(Pair(k.first, v), count)
                newCount.setOrAdd(Pair(v, k.second), count)
            } else {
                newCount.setOrAdd(k, count)
            }
        }

        pairCount = newCount
        val length = 1 + pairCount.entries.sumOf { it.value }
        println("length = $length")
    }

    val length = 1 + pairCount.entries.sumOf { it.value }
    println("length = $length")

    val countByElement = hashMapOf<Char, Long>().apply {
        pairCount.entries.forEach {
            this[it.key.first] = it.value + (this[it.key.first] ?: 0L)
            this[it.key.second] = it.value + (this[it.key.second] ?: 0L)
        }
    }

    // adjust for odd numbers (some elements are counted twice and some are once :/ )
    countByElement.forEach { (k, v) ->
        countByElement[k] = (v + 1) / 2
    }

    val maxByCount = countByElement.maxByOrNull { it.value }?.value!!
    val minByCount = countByElement.minByOrNull { it.value }?.value!!

    println("$maxByCount - $minByCount = ${maxByCount-minByCount}")

    return maxByCount - minByCount
}

fun main() {
    val testInput = readInput("aoc2021/day14/test.txt")

    println("test part1 = ${followInstructions(testInput)}")
    println("test part1 V2 = ${followInstructionsV2(testInput)}")

    println("test part2 = ${followInstructionsV2(testInput)}")

    val input = readInput("aoc2021/day14/input.txt")

    println("part1 = ${followInstructionsV2(input)}")
    println("part2 = ${followInstructionsV2(input)}")

    return
}
