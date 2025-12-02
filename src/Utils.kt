import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.measureTimedValue

fun day(i: Int, title: String) {
    println("Day $i: $title")
}

fun <T> part(i: Int, input: List<String>, block: (List<String>) -> T): T {
    val (output, duration) = measureTimedValue {
        block(input)
    }
    println("Part $i --> ${duration.inWholeMilliseconds}ms")
    return output
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Reads separated values from the given input txt file.
 */
fun splitInput(name: String, separator: String) = Path("src/$name.txt").readText().trim().split(separator)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
