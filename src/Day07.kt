fun main() {
    day(7, "Laboratories")

    val input = readLines("Day07")
//    val input = readLines("Day07_test")

    part(1, input) {
        val beams = MutableList(input.first().length) { false }
        beams[input.first().indexOf('S')] = true
        var splits = 0

        for (line in 2..<input.size) {
            input[line].forEachIndexed { i, ch ->
                if (ch == '^' && beams[i]) {
                    splits++
                    beams[i - 1] = true
                    beams[i + 1] = true
                    beams[i] = false
                }
            }
        }

        splits
    }.also { println("Splits, $it") }

    part(2, input) {
        val pathsAtIndex = LongArray(input.first().length)
        pathsAtIndex[input.first().indexOf('S')] = 1

        var totalPaths = 1L

        for (line in 2..input.size - 2) {
            var numNewPaths = 0L

            input[line].forEachIndexed { i, ch ->
                if (ch == '^') {
                    numNewPaths += pathsAtIndex[i]
                    pathsAtIndex[i - 1] += pathsAtIndex[i]
                    pathsAtIndex[i + 1] += pathsAtIndex[i]
                    pathsAtIndex[i] = 0
                }
            }

            totalPaths += numNewPaths
        }

        totalPaths
    }.also { println("Timelines, $it") }
}

