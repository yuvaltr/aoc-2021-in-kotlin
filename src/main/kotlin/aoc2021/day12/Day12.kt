package day12

import common.DirectedGraph
import common.toGraph
import readInput


fun part1(graph: DirectedGraph): Int {
    fun countPathsToEnd(graph: DirectedGraph, source: String, destination: String = "end"): Int {
        // assert starting node isn't visited already
        require(graph.nodes[source] == 0)

        // reached the destination - found a path
        if (source == destination)
            return 1

        // mark as visited only if non-capitalized (capitalized nodes are allowed to be visited more than once)
        if (source == source.lowercase())
            graph.nodes[source] = 1

        val adjacentNonVisitedNodes = graph.edges[source]?.filter { graph.nodes[it] == 0 } ?: listOf()

        val n = adjacentNonVisitedNodes.fold(0){ n, adj -> n + countPathsToEnd(graph, adj, destination) }

        graph.nodes[source] = 0

        return n
    }

    return countPathsToEnd(graph, "start", "end")
}

fun part2(graph: DirectedGraph): Int {
    fun countPathsToEnd(graph: DirectedGraph, partialPath: String, source: String, destination: String = "end"): Int {
        // assert starting node isn't visited twice already
        require(graph.nodes[source]!! < 2)

        if (graph.nodes.count { it.value == 2 } > 2)
            return 0

        // reached the destination - found a path
        if (source == destination) {
            //println("$partialPath,$destination")
            return 1
        }
        // mark as visited only if non-capitalized (capitalized nodes are allowed to be visited more than once)
        if (source == source.lowercase())
            graph.nodes[source] = graph.nodes[source]?.plus(1)!!

        val adjacentNonVisitedNodes = graph.edges[source]?.filter { graph.nodes[it]!! < 2 } ?: listOf()

        val n = adjacentNonVisitedNodes.fold(0){ n, adj -> n + countPathsToEnd(graph, if (partialPath.isNotBlank()) "$partialPath,$source" else source, adj, destination) }

        if (source == source.lowercase())
            graph.nodes[source] = graph.nodes[source]?.minus(1)!!

        return n
    }

    // allow "start" only once more
    graph.nodes["start"] = 1

    return countPathsToEnd(graph, "", "start", "end")
}

fun main() {
    val testInput = readInput("day12/test.txt").toGraph()

    println("test part1 = ${part1(testInput)}")
    println("test part2 = ${part2(testInput)}")

    val input = readInput("day12/input.txt").toGraph()

    println("part1 = ${part1(input)}")
    println("part2 = ${part2(input)}")

    return
}
