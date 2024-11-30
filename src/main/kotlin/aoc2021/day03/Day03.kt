package aoc2021.day03

import readInput
import kotlin.math.pow

fun main() {
    fun stringToBits(s: String): List<Int> {
        return s.toCharArray().map { c -> c.digitToInt() }
    }

    fun commonBits(input: List<String>, tieBreaker: Int = 1): List<Int> {
        val bitCount = input
            .map { stringToBits(it) }
            .reduce { sum, element -> sum.zip(element) { a, b -> a + b } }

        return bitCount
            .map {
                if (2 * it >= input.size)
                    tieBreaker
                else
                    1 - tieBreaker
            }
    }

    fun bitsToInt(bits: List<Int>): Int {
        var n = 0
        bits.reversed().forEachIndexed { index, i -> n += i * 2.0.pow(index.toDouble()).toInt() }
        return n
    }

    fun part1(input: List<String>): Int {
        val commonBits = commonBits(input)

        //val gamma1 = commonBits.foldRightIndexed(0) { i, element, sum -> sum + element * 2.0.pow(i.toDouble()).toInt() }
        val gamma = bitsToInt(commonBits)

        val epsilon = 2.0.pow((commonBits.size).toDouble()).toInt() - 1 - gamma // 2-complement

        return gamma * epsilon
    }

    fun filterByBit(input: List<String>, index: Int, desiredBit: Int /*Boolean*/): List<String> {
        return input.filter{ it.toCharArray()[index].digitToInt() == desiredBit }
    }

    fun filterByCommonBits(input: List<String>, tieBreaker: Int): Int {
        var filtered = input
        var index = 0
        while (filtered.size > 1) {
            val commonBits = commonBits(filtered, tieBreaker)
            filtered = filterByBit(filtered, index, commonBits[index])
            index += 1
        }

        return bitsToInt(stringToBits(filtered.single()))
    }

    fun part2(input: List<String>): Int {
        val oxygen = filterByCommonBits(input, tieBreaker = 1)
        val co2 = filterByCommonBits(input, tieBreaker = 0)

        return oxygen * co2
    }

    val testInput = readInput("aoc2021/day03/input.txt")
    check(testInput.size == 1000)

    println("part1 = ${part1(testInput)}")
    println("part2 = ${part2(testInput)}")
}
