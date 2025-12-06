import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
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
