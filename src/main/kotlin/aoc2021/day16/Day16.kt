package aoc2021.day16

import readInput

data class Packet(
    val version: Int,
    val type: Int,
    var value: Long? = null,
    val subPackets: List<Packet>
)

fun String.hexToBinary(): String {
    val hex = hashMapOf<Char, String>(
        '0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011",
        '4' to "0100", '5' to "0101", '6' to "0110", '7' to "0111",
        '8' to "1000", '9' to "1001", 'A' to "1010", 'B' to "1011",
        'C' to "1100", 'D' to "1101", 'E' to "1110", 'F' to "1111"
    )

    return this.toList().joinToString("") { hex[it]!! }
}

fun String.toDecimal(): Int {
    var ret = 0
    this.toList().map { it - '0' }.forEach { ret = it + 2 * ret }
    return ret
}

@JvmInline
value class Length(val v: Int)

fun String.toLiteralValue(): Pair<Long, Length> {
    var i = 0
    var value = 0L
    do {
        val fiver = this.substring(5 * i, 5 * (i + 1))
        value = value * 16 + fiver.substring(1).toDecimal()
        i += 1
    } while (fiver[0] == '1')

    return Pair(value, Length(i*5))
}

fun readPacket(it: String): Pair<Packet, Length> {
    val version = it.substring(0..2).toDecimal()
    val type = it.substring(3..5).toDecimal()

    if (type == 4) {
        val (value, length) = it.substring(6).toLiteralValue()
        return Pair(Packet(version, type, value, listOf()), Length(6 + length.v))

    } else {
        // operator
        val lengthType = it.substring(6..6).toDecimal()
        val subPackets = mutableListOf<Packet>()
        var startIndex: Int
        when (lengthType) {
            0 -> {
                var totalLength = it.substring(7..21).toDecimal()
                startIndex = 22
                while (totalLength > 0) {
                    val (packet, length) = readPacket(it.substring(startIndex))
                    startIndex += length.v
                    totalLength -= length.v
                    subPackets.add(packet)
                }
            }
            1 -> {
                var numSubPackets = it.substring(7..17).toDecimal()
                startIndex = 18
                while (numSubPackets > 0) {
                    val (packet, length) = readPacket(it.substring(startIndex))
                    startIndex += length.v
                    numSubPackets--
                    subPackets.add(packet)
                }
            }
            else -> {
                throw NotImplementedError()
            }
        }

        // A non-literal-value packet always has sub-packets (i.e. leaf packets are literal values)
        check(subPackets.size > 0)

        return Pair(Packet(version, type, null, subPackets), Length(startIndex))
    }
}

fun sumOfVersions(packet: Packet): Int {
    var verSum = packet.version
    verSum += packet.subPackets.sumOf { sumOfVersions(it) }
    return verSum
}

fun evaluate(packet: Packet): Long {
    packet.subPackets.forEach {
        if (it.value == null) {
            it.value = evaluate(it)
        }
    }

    packet.value = when (packet.type) {
        0 -> {
            packet.subPackets.sumOf { it.value!! }
        }
        1 -> {
            packet.subPackets.fold(1) { acc, it -> it.value!! * acc!! }
        }
        2 -> {
            packet.subPackets.minOf { it.value!! }
        }
        3 -> {
            packet.subPackets.maxOf { it.value!! }
        }
        4 -> {
            packet.value!!
        }
        5 -> {
            check(packet.subPackets.size == 2)
            if (packet.subPackets.first().value!! > packet.subPackets.last().value!!)
                1
            else
                0
        }
        6 -> {
            check(packet.subPackets.size == 2)
            if (packet.subPackets.first().value!! < packet.subPackets.last().value!!)
                1
            else
                0
        }
        7 -> {
            check(packet.subPackets.size == 2)
            if (packet.subPackets.first().value!! == packet.subPackets.last().value!!)
                1
            else
                0
        }
        else -> {
            throw NotImplementedError()
        }
    }

    return packet.value!!
}

fun part1(input: List<String>): Int {
    input.forEach {
        val binary = it.hexToBinary()
        val (packet, _) = readPacket(binary)
        val sumVersions = sumOfVersions(packet)
        val shortPacket = if (it.length > 20) "${it.take(17)}..." else it

        println("packet $shortPacket has sumVer = $sumVersions")
    }
    return 0
}

fun part2(input: List<String>): Int {
    input.forEach {
        val binary = it.hexToBinary()
        val (packet, _) = readPacket(binary)
        val eval = evaluate(packet)
        val shortPacket = if (it.length > 20) "${it.take(17)}..." else it

        println("packet $shortPacket has value = $eval")
    }
    return 0
}

fun main() {
    val testInput = readInput("aoc2021/day16/test.txt")

    println("test part1 = ${part1(testInput)}")
    //println("test part2 = ${part2(testInput)}")

    val input = readInput("aoc2021/day16/input.txt")

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
