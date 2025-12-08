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
            val root = dsu.getParent(i) // path compression ensures current root
            sizesByRoot[root] = (sizesByRoot[root] ?: 0) + 1
        }

        sizesByRoot.values.sortedDescending().take(3).reduce { acc, n -> acc * n }
    }.also { println("Mult 3 Largest Circuits, $it") }

    part(2, input) {
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

        var i = 0
        while (dsu.setSize.max() != cptTrips.size) {
            val (nodeA, nodeB, _) = edgesList[i]
            dsu.union(nodeA, nodeB)
            i++
        }

        with(edgesList[i - 1]) {
            cptTrips[iNodeA].first.toLong() * cptTrips[iNodeB].first.toLong()
        }
    }.also { println("Outlet Distance, $it") }
}