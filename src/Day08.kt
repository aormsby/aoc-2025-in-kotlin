import kotlin.math.sqrt

fun main() {
    day(8, "Playground")

    val input = readLines("Day08")
//    val input = readLines("Day08_test")

    part(1, input) {
        data class Edge(
            val iNodeA: Int,
            val iNodeB: Int,
            val dist: Double,
        )

        val edgesList = mutableListOf<Edge>()
        val cptTrips = input.map {
            val (x, y, z) = it.split(',').map(String::toDouble)
            Triple(x, y, z)
        }

        for (i in 0..<cptTrips.size) {
            for (j in (i + 1)..<cptTrips.size) {
                if (i != j) {
                    val dist = euclidDist3D(cptTrips[i], cptTrips[j])
                    edgesList.add(Edge(i, j, dist))
                }
            }
        }

        val dsu = DisjointSetUnion(cptTrips.size)
        edgesList.sortBy { it.dist }

        for (step in 0..<1000) {
            val (nodeA, nodeB, _) = edgesList[step]
            dsu.union(nodeA, nodeB)
        }

        val sizesByRoot = mutableMapOf<Int, Int>()
        for (i in 0 until cptTrips.size) {
            val r = dsu.getParent(i) // path compression ensures current root
            sizesByRoot[r] = (sizesByRoot[r] ?: 0) + 1
        }

        sizesByRoot.values.sortedDescending().take(3).reduce { acc, n -> acc * n }
    }.also { println("Mult 3 Largest Circuits, $it") }

    part(2, input) {
        null
    }.also { println("tbd, $it") }
}

fun euclidDist3D(p1: Triple<Double, Double, Double>, p2: Triple<Double, Double, Double>): Double {
    val dx = (p1.first - p2.first)
    val dy = (p1.second - p2.second)
    val dz = (p1.third - p2.third)
    return sqrt((dx * dx) + (dy * dy) + (dz * dz))
}

// union-find
class DisjointSetUnion(numNodes: Int) {
    // 'it' represents and populates 0..numNodes
    private val parent = IntArray(numNodes) { it }
    val setSize = IntArray(numNodes) { 1 }

    fun getParent(node: Int): Int {
        var cur = node
        while (parent[cur] != cur) {
            // active path compression
            parent[cur] = parent[parent[cur]]
            cur = parent[cur]
        }
        return cur
    }

    fun union(nodeA: Int, nodeB: Int): Boolean {
        var parA = getParent(nodeA)
        var parB = getParent(nodeB)

        if (parA == parB)
            return false

        // swap parent fields to always parent the smaller set to the other
        if (setSize[parA] < setSize[parB]) {
            val temp = parA
            parA = parB
            parB = temp
        }

        parent[parB] = parA
        setSize[parA] += setSize[parB]

        return true
    }

    override fun toString(): String =
        "${parent.joinToString(", ")}\n${setSize.joinToString(", ")}"
}