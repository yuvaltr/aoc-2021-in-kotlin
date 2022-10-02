package common


data class DirectedGraph(
    val nodes: LinkedHashMap<String, Int> = linkedMapOf(),
    val edges: LinkedHashMap<String, MutableList<String>> = linkedMapOf()
)


fun List<String>.toGraph(): DirectedGraph {
    val graph = DirectedGraph()

    this.forEach { line ->
        val regex = """([A-Z|a-z]+)-([A-Z|a-z]+)""".toRegex()
        val (first, second) = regex.find(line)!!.destructured.toList()

        graph.nodes[first] = 0
        graph.nodes[second] = 0

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
