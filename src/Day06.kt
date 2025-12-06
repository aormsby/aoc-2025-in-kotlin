fun main() {
    day(6, "Trash Compactor")

    val input = readLines("Day06")
//    val input = readLines("Day06_test")

    part(1, input) {
        val iters = input.map { it.iterator() }
        var totalSum = 0L

        while (iters.all { it.hasNext() }) {
            val folded = iters.runningFold("") { _, iter ->
                var str = ""
                var nonSpaceFound = false
                while (iter.hasNext()) {
                    val next = iter.next()
                    if (next != ' ') {
                        nonSpaceFound = true
                        str += next
                    }
                    if (next == ' ' && nonSpaceFound) {
                        break
                    }
                }
                str.trim()
            }.drop(1)
//            print(folded)

            val result = when (folded.last()) {
                "+" -> folded.dropLast(1).sumOf { it.toLong() }

                "-" -> folded.dropLast(1).fold(folded[1].toLong()) { acc, n ->
                    acc - n.toLong()
                }

                else -> folded.dropLast(1).fold(1L) { acc, n ->
                    acc * n.toLong()
                }
            }
//            println(" : $result")

            totalSum += result
        }

        totalSum
    }.also { println("LTR Sum, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}
