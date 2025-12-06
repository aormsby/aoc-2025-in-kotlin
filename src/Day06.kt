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
        val lists = transposeStrings(input, ' ')
        var totalSum = 0L

        var i = lists.size - 1
        val ops = listOf('*', '+', '-')
        while (i >= 0) {
            var curOp = ' '
            val nums = mutableListOf<Long>()

            while (curOp == ' ') {
                val next = lists[i]
                if (next.last() in ops) {
                    curOp = next.last()
                    nums.add(next.dropLast(1).trim().toLong())
                    i--
                } else if (next.isNotBlank()) {
                    nums.add(next.trim().toLong())
                    i--
                } else {
                    i--
                }
            }
//            print(nums)

            val result = when (curOp) {
                '+' -> nums.sum()
                '-' -> nums.reduce { acc, n -> acc - n }
                else -> nums.reduce { acc, n -> acc * n }
            }
//            println(" : $result")

            totalSum += result
        }

        totalSum
    }.also { println("Japanese Sum, $it") }
}
