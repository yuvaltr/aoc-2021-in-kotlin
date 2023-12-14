package euler800

import java.lang.Exception
import kotlin.math.log10
import kotlin.system.measureTimeMillis


class PrimeGenerator {
    companion object {
        private var primes = listOf<Int>()

        fun get(n: Int): List<Int> {
            if (primes.size >= n)
                return primes.subList(0, n)

            primes = generateSequence(2 to generateSequence(3) { it + 2 }) {
                val currSeq = it.second.iterator()
                val nextPrime = currSeq.next()
                nextPrime to currSeq.asSequence().filter { it % nextPrime != 0 }
            }.map { it.first }.take(n).toList()

            return primes
        }
    }
}


inline fun <T> Array<T>.lastOfSortedOrNull(predicate: (T) -> Boolean): Pair<T?, Int> {
    var rightIterator = this.lastIndex
    var leftIterator = 0
    while (leftIterator < rightIterator) {
        val mid = (rightIterator - leftIterator + 1) / 2 + leftIterator
        if (predicate(this[mid])) {
            leftIterator = mid + 1
        } else {
            rightIterator = mid - 1
        }
    }

    val i = if (predicate(this[rightIterator]))
        rightIterator
    else
        rightIterator - 1

    val ret = if (i >= 0)
        this[i]
    else
        null

    check(predicate(this[i]) && !predicate(this[i+1]))

    return Pair(ret, rightIterator - 1)
}


fun main() {
    val m = 800

    val primes: Array<Int>
    val elapsed = measureTimeMillis {
        primes = PrimeGenerator.get(2400).toTypedArray()
    }

    val largestPrime = primes.last()
    println("elapsed = $elapsed ms, largest prime = $largestPrime")

    var results = 0

    for ((index, p) in primes.withIndex()) {
        val (b, bIndex) = primes.lastOfSortedOrNull { p.toDouble() * log10(it.toDouble()) + it.toDouble() * log10(p.toDouble()) <= m * log10(m.toDouble()) }
        if (b == null || b < p)
            break

        results += (bIndex - index)
    }

    println("counted $results")
}
