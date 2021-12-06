package day03

import readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val bitCount = input
            .map { it -> it.toCharArray().map{ c -> c - '0'} }
            .reduce { sum, element -> sum.zip(element){ a, b -> a + b} }
        
        val commonBits = bitCount
            .map { if (it > input.size / 2) 1 else 0 }

        //val gamma1 = commonBits.foldRightIndexed(0) { i, element, sum -> sum + element * 2.0.pow(i.toDouble()).toInt() }

        var gamma = 0
        commonBits.reversed().forEachIndexed { index, i -> gamma += i * 2.0.pow(index.toDouble()).toInt() }

        val epsilon = 2.0.pow((commonBits.size).toDouble()).toInt() - 1 - gamma // 2-complement

        return gamma * epsilon
    }

    val testInput = readInput("day03/input.txt")

    check(testInput.size == 1000)
    println("part1 = ${part1(testInput)}")
}
