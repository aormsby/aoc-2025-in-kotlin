fun main() {
    day(6, "Trash Compactor")

    val input = readLines("Day06")
//    val input = readLines("Day06_test")

    part(1, input) {
        val maxLength = input.maxOf { it.length }
        val lines = input.map { str -> str.padEnd(maxLength, ' ') }
        val indices = MutableList(lines.size) { 0 }
        var totalSum = 0L

        while (indices.all { it < maxLength }) {
            var curOp = ' '
            val curNums = mutableListOf<Long>()

            for (i in 0..<lines.size - 1) {
                val str = buildString {
                    while (indices[i] < lines[i].length) {
                        val next = lines[i][indices[i]]
                        indices[i] += 1
                        if (next != ' ') {
                            append(next)
                        } else if (isNotBlank()) {
                            break
                        }
                    }
                }
                curNums.add(str.toLong())
            }

            while (lines.last().isNotEmpty()) {
                val next = lines.last()[indices.last()]
                indices[indices.size - 1] += 1

                if (next != ' ') {
                    curOp = next
                    break
                }
            }

            val result = when (curOp) {
                '+' -> curNums.sum()
                '-' -> curNums.reduce { acc, n -> acc - n }
                else -> curNums.reduce { acc, n -> acc * n }
            }
//            println("$curNums : $result")

            totalSum += result
        }

        totalSum
    }.also { println("LTR Sum, $it") }

    part(2, input) {
        val lists = input.map { str -> str.padEnd(input.maxOf { it.length }, ' ') }
        var totalSum = 0L

        val ops = listOf('*', '+', '-')
        var curOp = ' '
        val curNums = mutableListOf<Long>()

        for (i in (lists.first().length - 1) downTo 0) {
            val str = buildString {
                for (r in 0..<lists.size) {
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
