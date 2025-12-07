fun main() {
    day(7, "Laboratories")

    val input = readLines("Day07")
//    val input = readLines("Day07_test")

    part(1, input) {
        val beams = MutableList(input.first().length) { false }
        beams[input.first().indexOf('S')] = true
        var splits = 0

        for (line in 1..<input.size) {
            input[line].forEachIndexed { i, ch ->
                if (ch == '^' && beams[i]) {
                    splits++
                    beams[i] = false
                    beams[i - 1] = true
                    beams[i + 1] = true
                }
            }
        }

        splits
    }.also { println("Splits, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}
