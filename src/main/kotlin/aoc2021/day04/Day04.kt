package day04

import readInput

class BingoBoard {
    private lateinit var numbers: IntArray // = Array(size = 5) { IntArray(size = 5) }
    private lateinit var marked: BooleanArray // = Array(size = 5) { BooleanArray(size = 5) }
    private var won = false

    constructor()

    constructor(strings: List<String>): this() {
        check(strings.size == 5)

        numbers = strings
            .map { it.trim().split("\\s+".toRegex()).map { n -> n.toInt() } }
            .flatten().toIntArray()

        check(numbers.size == 25)

        marked = BooleanArray(size = 25)
    }

    fun mark(n: Int) {
        val i = numbers.indexOf(n)
        if (i < 0)
            return

        marked[i] = true

        val rowIdx = generateSequence(5 * (i / 5)) { it + 1 }.take(5).toList()
        val columnIdx = generateSequence(i % 5) { it + 5 }.take(5).toList()

        if (marked.slice(rowIdx).all { it } || marked.slice(columnIdx).all { it })
            won = true
    }

    fun won(): Boolean {
        return won
    }

    fun sumUnmarked(): Int {
        return numbers.toList().zip(marked.toList()) { num, marked -> if (marked) 0 else num }.reduce { acc, i -> acc + i }
    }
}

fun main() {
    fun part1(guessed: List<Int>, boards: List<BingoBoard>): Int {
        guessed.forEach { guessedNumber ->
            boards.forEach { board ->
                board.mark(guessedNumber)
                if (board.won()) {
                    return guessedNumber * board.sumUnmarked()
                }
            }
        }

        return 0
    }

    fun part2(guessed: List<Int>, boards: List<BingoBoard>): Int {
        guessed.forEach { guessedNumber ->
            val boardsToPlay = boards.filter{ !it.won() }
            val nBoards = boardsToPlay.size

            boardsToPlay.forEach { board ->
                board.mark(guessedNumber)
                if (nBoards == 1 && board.won()) {
                    return guessedNumber * board.sumUnmarked()
                }
            }
        }

        return 0
    }

    val testInput = readInput("day04/input.txt")

    val guessed: List<Int> = testInput[0].split(',').map { it.toInt() }
    val boards = testInput.drop(2).windowed(size = 5, step = 6).map { BingoBoard(it) }

    println("part1 = ${part1(guessed, boards)}")
    println("part2 = ${part2(guessed, boards)}")
}
