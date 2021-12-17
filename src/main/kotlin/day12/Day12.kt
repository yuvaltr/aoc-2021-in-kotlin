package day12

import readInput


data class Graph(
    val nodes: LinkedHashMap<String, Boolean> = linkedMapOf(),
    val edges: LinkedHashMap<String, MutableList<String>> = linkedMapOf()
)

fun List<String>.toGraph(): Graph {
    val graph = Graph()

    this.forEach { line ->
        val regex = """([A-Z|a-z]+)-([A-Z|a-z]+)""".toRegex()
        val (first, second) = regex.find(line)!!.destructured.toList()

        graph.nodes[first] = false
        graph.nodes[second] = false

        if (graph.edges.containsKey(first)) {
            if (!graph.edges[first]!!.contains(second))
                graph.edges[first]!!.add(second)
        } else {
            graph.edges[first] = mutableListOf(second)
        }

        if (graph.edges.containsKey(second)) {
            if (!graph.edges[second]!!.contains(first))
                graph.edges[second]!!.add(first)
        } else {
            graph.edges[second] = mutableListOf(first)
        }
    }

    return graph
}


fun part1(graph: Graph): Int {
    fun countPathsToEnd(graph: Graph, source: String, destination: String = "end"): Int {
        // assert starting node isn't visited already
        require(graph.nodes[source] == false)

        // reached the destination - found a path
        if (source == destination)
            return 1

        // mark as visited only if non-capitalized (capitalized nodes are allowed to be visited more than once)
        if (source == source.lowercase())
            graph.nodes[source] = true

        val adjacentNonVisitedNodes = graph.edges[source]?.filter { graph.nodes[it] == false } ?: listOf()

        val n = adjacentNonVisitedNodes.fold(0){ n, adj -> n + countPathsToEnd(graph, adj, destination) }

        graph.nodes[source] = false

        return n
    }

    return countPathsToEnd(graph, "start", "end")
}

fun part2(graph: Graph): Long {

    return 1
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
