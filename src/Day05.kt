import kotlin.math.max
import kotlin.math.min

fun main() {
    day(5, "Cafeteria")

    val input = readMultiInput("Day05")
//    val input = readMultiInput("Day05_test")

    part(1, input) {
        val ranges = input.first().map { strRange ->
            strRange.substringBefore('-').toLong()..strRange.substringAfter('-').toLong()
        }

        input.last().fold(0) { acc, num ->
            if (ranges.any { num.toLong() in it })
                acc + 1
            else acc
        }
    }.also { println("Matched Fresh IDs, $it") }

    part(2, input) {
        val ranges = ArrayDeque<LongRange>().apply {
            addAll(
                input.first().map { strRange ->
                    strRange.substringBefore('-').toLong()..strRange.substringAfter('-').toLong()
                }.sortedBy { it.first }
            )
        }

        var count = 0L
        while (ranges.isNotEmpty()) {
            if (ranges.size == 1) {
                count += (ranges[0].last - ranges[0].first + 1)
                ranges.removeFirst()
                continue
            }

            when {
                ranges[0].last < ranges[1].first -> {
                    count += (ranges[0].last - ranges[0].first + 1)
                    ranges.removeFirst()
                }

                else -> {
                    val combined = min(ranges[0].first, ranges[1].first)..max(ranges[0].last, ranges[1].last)
                    ranges.removeFirst()
                    ranges.removeFirst()
                    ranges.addFirst(combined)
                }
            }
        }

        count
    }.also { println("All Fresh IDs, $it") }
}
