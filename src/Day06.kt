fun main() {
    day(6, "Trash Compactor")

    val input = readLines("Day06")
//    val input = readLines("Day06_test")

    fun List<Long>.doOp(op: Char) =
        when (op) {
            '+' -> sum()
            '-' -> reduce { acc, n -> acc - n }
            else -> reduce { acc, n -> acc * n }
        }

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

            val result = curNums.doOp(curOp)
//            println("$curNums : $result")
            totalSum += result
        }

        totalSum
    }.also { println("LTR Sum, $it") }

    part(2, input) {
        val maxLength = input.maxOf { it.length }
        val lists = input.map { str -> str.padEnd(maxLength, ' ') }
        var totalSum = 0L

        val ops = listOf('*', '+', '-')
        var curOp = ' '
        val curNums = mutableListOf<Long>()

        for (i in (maxLength - 1) downTo 0) {
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
                val result = curNums.doOp(curOp)
//                println("$curNums : $result")

                totalSum += result
                curOp = ' '
                curNums.clear()
            }
        }

        totalSum
    }.also { println("Japanese Sum, $it") }
}
