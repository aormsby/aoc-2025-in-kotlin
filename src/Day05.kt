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
    }.also { println("Fresh IDs, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}
