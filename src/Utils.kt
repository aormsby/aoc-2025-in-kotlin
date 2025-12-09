import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

fun day(i: Int, title: String) {
    println("Day $i: $title")
}

fun <TInput, TResult> part(i: Int, input: TInput, block: (TInput) -> TResult): TResult {
    val (output, duration) = measureTimedValue {
        block(input)
    }
    println("Part $i --> ${duration.inWholeMilliseconds}ms")
    return output
}

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Reads separated values from the given input txt file.
 */
fun splitInput(name: String, separator: String) = Path("src/$name.txt").readText().trim().split(separator)

/**
 * Reads lines into 2D array of chars from the given text file.
 */
fun list2DInput(name: String) = readLines(name).map { line -> line.toMutableList() } as MutableList

/**
 * Reads grouped lines into separate lists of strings.
 */
fun readMultiInput(name: String) = Path("src/$name.txt").readText().trim().split("\n\n").map { it.lines() }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Quick pairs to add to current array coords to get vertical and horizontal neighbors.
 */
val crossNeighbors = listOf(
    -1 to 0, 1 to 0, 0 to -1, 0 to 1,
)

/**
 * Quick pairs to add to current array coords to get diagonal neighbors.
 */
val diagNeighbors = listOf(
    -1 to 1, 1 to 1, 1 to -1, -1 to -1,
)

/**
 * Quick pairs to add to current array coords to get all neighbors.
 */
val allNeighbors = crossNeighbors + diagNeighbors

/**
 * Helper to filter out invalid coordinates for neighbors that would be out of bounds.
 */
fun validNeighbors(
    numRows: Int,
    rowLength: Int,
    rowI: Int? = null,
    colI: Int? = null,
    startNeighbors: List<Pair<Int, Int>>? = allNeighbors,
): List<Pair<Int, Int>> {
    var n = startNeighbors ?: allNeighbors

    n = when (rowI) {
        0 -> n.filterNot { it.first == -1 }
        numRows - 1 -> n.filterNot { it.first == 1 }
        else -> n
    }

    n = when (colI) {
        0 -> n.filterNot { it.second == -1 }
        rowLength - 1 -> n.filterNot { it.second == 1 }
        else -> n
    }

    return n
}

/**
 * Transpose matrix. Yup.
 */
fun <T> transpose(matrix: List<List<T>>): List<List<T>> {
    val numCols = matrix.first().size
    return List(numCols) { r -> List(matrix.size) { c -> matrix[c][r] } }
}

/**
 * Transpose a List<String> by Char (since other transpose() doesn't recognize Strings as List<Char>)
 */
fun transposeStrings(rows: List<String>, pad: Char? = null): List<String> {
    val modified = pad?.let { p ->
        rows.map { str ->
            str.padEnd(rows.maxOf { r -> r.length }, p)
        }
    } ?: rows

    val numCols = modified.first().length
    return List(numCols) { c ->
        buildString(modified.size) {
            for (r in modified.indices) {
                append(modified[r][c])
            }
        }
    }
}

/**
 * Calculate straight line distance between 3D points
 */
fun euclidDist3D(p1: Triple<Double, Double, Double>, p2: Triple<Double, Double, Double>): Double {
    val dx = (p1.first - p2.first)
    val dy = (p1.second - p2.second)
    val dz = (p1.third - p2.third)
    return sqrt((dx * dx) + (dy * dy) + (dz * dz))
}

/**
 * Calculates area of Rect between 2D points
 */
fun rectArea(p1: GridPoint, p2: GridPoint): Long =
    (abs(p1.col.toLong() - p2.col.toLong()) + 1) * (abs(p1.row.toLong() - p2.row.toLong()) + 1)


/**
 * For union-find
 */
class DisjointSetUnion(numNodes: Int) {
    // 'it' represents and populates 0..numNodes
    val parent = IntArray(numNodes) { it }
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

/**
 * Attempt to merge overlapping ranges, return combined or null
 */
fun mergeRanges(r1: IntRange?, r2: IntRange?): IntRange? {
    if (r1 == null || r2 == null)
        return null

    val (a, b) =
        if (r1.first <= r2.first) r1 to r2
        else r2 to r1

    return when {
        // does not intersect
        a.last < b.first -> null

        // intersects, return combined
        else -> min(a.first, b.first)..max(a.last, b.last)
    }
}

data class GridPoint(
    val col: Int, // x
    val row: Int, // y
)

data class AocRect(
    val corner1: GridPoint,
    val corner2: GridPoint,
    val area: Long,
)