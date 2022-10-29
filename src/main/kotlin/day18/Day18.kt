package day18

import readInput

interface INumberElement {

}

data class RegularNumber(
    var v: Int
): INumberElement {

    override fun toString(): String {
        return v.toString()
    }
}

data class SnailfishNumber(
    var left: INumberElement,
    var right: INumberElement
): INumberElement {

    override fun toString(): String {
        return "[${left.toString()},${right.toString()}]"
    }

    companion object {
        fun fromString(me: String): SnailfishNumber {

            fun getSnailfishElement(number: String): INumberElement {
                if (number.first() != '[') {
                    require(number.toList().all { it.isDigit() }) { "expected a regular (positive) integer" }
                    return RegularNumber(number.toInt())
                }

                require(number.first() == '[' && number.last() == ']') { "expected a pair in square brackets ('[]')" }

                var dividingCommaIndex: Int = -1
                var openBrackets = 0
                number.toList().forEachIndexed { i, c ->
                    if (c == '[')
                        openBrackets += 1
                    else if (c == ']')
                        openBrackets -= 1

                    if (c == ',' && openBrackets == 1)
                        dividingCommaIndex = i

                    check(openBrackets >= 0)
                }

                check(openBrackets == 0)

                val left = number.substring(1, dividingCommaIndex)
                val right = number.substring(dividingCommaIndex + 1, number.lastIndex)
                return SnailfishNumber(getSnailfishElement(left), getSnailfishElement(right))
            }

            getSnailfishElement(me).apply {
                check(this is SnailfishNumber)
                return this
            }
        }
    }

    fun add(other: SnailfishNumber): SnailfishNumber {
        return SnailfishNumber(this, other).apply {
            this.reduce()
        }
    }

    enum class Direction {
        LEFT,
        RIGHT
    }

    private fun reduce() {

        fun explode(): Boolean {
            fun findNeedsExplode(number: SnailfishNumber, depth: Int) {

            }

            val traversal = mutableListOf<Direction>()
            val depth = 0
            findNeedsExplode(this,0)


            return false
        }

        fun split(): Boolean {

            return false
        }

        while (explode()) {
            reduce()
        }

        while (split()) {
            reduce()
        }
    }
}

fun readInstructions(input: List<String>): List<SnailfishNumber> {
    return input.map { SnailfishNumber.fromString(it) }
}

fun followInstructions(input: List<String>) {
    //val numbers = readInstructions(input)
    val numbers = readInstructions(listOf("[[[[4,3],4],4],[7,[[8,4],9]]]","[1,1]"))
    val s = numbers.first().add(numbers.last())
    val a = 1
}



fun main() {
    val testInput = readInput("day18/test.txt")
    val input = readInput("day18/input.txt")

    println("test part1 = ${followInstructions(testInput)}")
    //println("part1 = ${followInstructions(input)}")

    //println("test part2 = ${followInstructions(testInput)}")
    //println("part2 = ${followInstructions(input)}")

    return
}
