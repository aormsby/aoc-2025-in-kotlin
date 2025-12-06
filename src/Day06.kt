fun main() {
    day(6, "Trash Compactor")

    val input = readLines("Day06")
//    val input = readLines("Day06_test")

    val ops = listOf('*', '+', '-')

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
        val lists = input.map { str -> str.padEnd(input.maxOf { it.length }, ' ') }
        var totalSum = 0L
        val numRows = lists.size

        var curOp = ' '
        val curNums = mutableListOf<Long>()

        for (i in (lists.first().length - 1) downTo 0) {
            val str = buildString {
                for (r in 0..<numRows) {
                    append(lists[r][i])
                }
            }.trim()

            if (str.isBlank()) {
                continue
            } else if (str.last() in ops) {
                curOp = str.last()
                curNums.add(str.dropLast(1).trim().toLong())
            } else {
                curNums.add(str.trim().toLong())
            }

            if (curOp != ' ') {
                val result = when (curOp) {
                    '+' -> curNums.sum()
                    '-' -> curNums.reduce { acc, n -> acc - n }
                    else -> curNums.reduce { acc, n -> acc * n }
                }
//            println("$curNums : $result")

                totalSum += result
                curOp = ' '
                curNums.clear()
            }
        }

        totalSum
    }.also { println("Japanese Sum, $it") }
}
