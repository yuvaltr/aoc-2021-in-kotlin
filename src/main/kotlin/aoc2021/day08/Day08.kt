package day08

import readInput

fun toSegmentedDigits(line: String): List<String> {
    val regex = """([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+) \| ([a-g]+) ([a-g]+) ([a-g]+) ([a-g]+)""".toRegex()
    return regex.find(line)!!.destructured.toList()
}

fun part1(input: List<String>): Int {
    return input
        .map { line -> toSegmentedDigits(line)
            .slice(listOf(10, 11, 12, 13))
            .count { it.length in listOf(2, 3, 4, 7) } }
        .reduce { acc, i -> acc + i }
}

fun <V> List<V>.permutations(): List<List<V>> {
    val retVal: MutableList<List<V>> = mutableListOf()


    fun <V> swap(list: MutableList<V>, i: Int, i1: Int) {
        val tmp = list[i]
        list[i] = list[i1]
        list[i1] = tmp
    }

    fun generate(k: Int, list: MutableList<V>) {
        // If only 1 element, just output the array
        if (k == 1) {
            retVal.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toMutableList())
    return retVal
}

fun asDigitsOrNull(line: String, permutation: List<Char>): List<Int>? {
    fun permute(digitSegments: List<String>, permutation: List<Char>): List<String> {
        return digitSegments.map { digitSegment ->

            var newDigitSegment = digitSegment

            ('a'..'g').forEach { char ->
                newDigitSegment = newDigitSegment.replace(char, permutation[char - 'a']) }

            newDigitSegment.lowercase().toList().sorted().joinToString ("")
        }
    }

    fun isSevenSegmentDigit(segmentMap: String): Int? {
        return when (segmentMap) {
            "abcefg" -> 0
            "cf" -> 1
            "acdeg" -> 2
            "acdfg" -> 3
            "bcdf" -> 4
            "abdfg" -> 5
            "abdefg" -> 6
            "acf" -> 7
            "abcdefg" -> 8
            "abcdfg" -> 9
            else -> null
        }
    }

    val permuted = permute(toSegmentedDigits(line), permutation)
    val asDigits = permuted.map {
        isSevenSegmentDigit(it)
    }
    if (asDigits.all { it != null })
        return asDigits as List<Int>

    return null
}

fun part2(input: List<String>): Int {
    // verify all entries contain "1", "4" and "7"
    val c =  input
        .map { line -> toSegmentedDigits(line)
            .map { list -> list.length }
            .containsAll(listOf(2,3,4)) }
        .count { it }

    check(c == input.size)

    // verify a specific solution
    asDigitsOrNull("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf", "CFGABDE".uppercase().toList())

    val perms = "ABCDEFG".toList().permutations()

    var sum = 0

    input.forEach { line ->
        var foundSolution = false
        perms.forEach { permutation ->
            val asDigits = asDigitsOrNull(line, permutation)
            if (asDigits != null) {
                if (foundSolution)
                    throw Exception("solution should be unique")

                foundSolution = true
                sum += 1000*asDigits[10] + 100*asDigits[11] + 10*asDigits[12] + asDigits[13]
            }
        }

        if (!foundSolution)
            throw Exception("solution should be found")
    }

    return sum
}

fun main() {

    val testInput = readInput("day08/test.txt")

    println("part1 = ${part1(testInput)}")
    println("part2 = ${part2(testInput)}")

    return
}
