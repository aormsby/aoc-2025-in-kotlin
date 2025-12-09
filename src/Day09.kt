import kotlin.math.max
import kotlin.math.min

fun main() {
    day(9, "Movie Theater")

    val input = readLines("Day09")
//    val input = readLines("Day09_test")

    part(1, input) {
        val coords = input.map { line ->
            val (col, row) = line.split(',').map { it.toInt() }
            GridPoint(col, row)
        }
        val areaList = mutableListOf<AocRect>()

        for (i in 0..<coords.size) {
            for (j in (i + 1)..<coords.size) {
                val (a, b) = coords[i] to coords[j]
                val area = rectArea(coords[i], coords[j])
                areaList.add(AocRect(a, b, area))
            }
        }

        areaList.maxOf { it.area }
    }.also { println("Largest Area, $it") }

    part(2, input) {
        val coords = input.map { line ->
            val (col, row) = line.split(',').map { it.toInt() }
            GridPoint(col, row)
        }
        val validLineRanges = mutableMapOf<Int, IntRange>()

        // Apparently, it's safe to assume there wouldn't be multiple valid ranges in a row. Lucky me!
        (coords + coords.first())
            .windowed(2)
            .forEach { (a, b) ->
                val rowMin = min(a.row, b.row)
                val rowMax = max(a.row, b.row)
                val newRange = min(a.col, b.col)..max(a.col, b.col)

                // loops one row if same min/max, range if not)
                for (line in rowMin..rowMax) {
                    val prev = validLineRanges[line]
                    validLineRanges[line] = prev?.let {
                        min(newRange.first, prev.first)..max(newRange.last, prev.last)
                    } ?: newRange
                }
            }
        
        val areaList = mutableListOf<AocRect>()
        for (i in 0..<coords.size) {
            for (j in (i + 1)..<coords.size) {
                val (a, b) = coords[i] to coords[j]
                val rowRange = min(a.row, b.row)..max(a.row, b.row)
                val colEnds = listOf(min(a.col, b.col), max(a.col, b.col))

                var doArea = true
                for (r in rowRange) {
                    val vlr = validLineRanges[r]!!
                    if (colEnds.any { c -> c !in vlr }) {
                        doArea = false
                        break
                    }
                }

                if (doArea) {
                    val area = rectArea(a, b)
                    areaList.add(AocRect(a, b, area))
                }
            }
        }

        areaList.maxOf { it.area }
    }.also { println("Largest Valid Area, $it") }
}