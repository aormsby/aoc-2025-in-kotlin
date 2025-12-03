fun main() {
    day(3, "Lobby")

    val input = readInput("Day03")
//    val input = readInput("Day03_test")

    part(1, input) {
        input.sumOf { strBank ->
            val intBank = strBank.map { it.digitToInt() }
            val size = intBank.size
            val pack = MutableList(2) { 0 }
            var mark = 0

            for (i in 0 until size) {
                val cur = intBank[i]
                val remaining = size - 1 - i

                // check for lower value
                val lowerNumI = pack.indexOfFirst { it != 0 && it < cur }
                if (lowerNumI != -1) {
                    val remainingFromLowerI = 1 - lowerNumI
                    mark = 1 - minOf(remainingFromLowerI, remaining)
                    for (j in mark..1) {
                        pack[j] = 0
                    }
                }
                if (mark <= 1) pack[mark] = cur
                mark = (mark + 1)
            }

//            println(pack.joinToString("").toLong())
            pack.joinToString("").toLong()
        }
    }.also { println("Sum Max Joltage 2, $it") }

    part(2, input) {
        input.sumOf { strBank ->
            val intBank = strBank.map { it.digitToInt() }
            val size = intBank.size
            val pack = MutableList(12) { 0 }
            var mark = 0

            for (i in 0 until size) {
                val cur = intBank[i]
                val remaining = size - 1 - i

                // check for lower value
                val lowerNumI = pack.indexOfFirst { it != 0 && it < cur }
                if (lowerNumI != -1) {
                    val remainingFromLowerI = 11 - lowerNumI
                    mark = 11 - minOf(remainingFromLowerI, remaining)
                    for (j in mark..11) {
                        pack[j] = 0
                    }
                }
                if (mark <= 11) pack[mark] = cur
                mark = (mark + 1)
            }

//            println(pack.joinToString("").toLong())
            pack.joinToString("").toLong()
        }
    }.also { println("Sum Max Joltage 12, $it") }
}
