fun main() {
    day(3, "Lobby")

    val input = readLines("Day03")
//    val input = readLines("Day03_test")

    part(1, input) {
        input.sumOf { inp ->
            val bank = inp.map { it.digitToInt() }
            val bSize = bank.size
            val pack = MutableList(2) { 0 }
            var pMark = 0

            for (i in 0 until bSize) {
                val cur = bank[i]
                val bankRemaining = bSize - 1 - i

                // check for lower value
                val iLowerNum = pack.indexOfFirst { it != 0 && it < cur }
                if (iLowerNum != -1) {
                    val remainingFromLower = 1 - iLowerNum
                    pMark = 1 - minOf(remainingFromLower, bankRemaining)
                    for (j in pMark..1) {
                        pack[j] = 0
                    }
                }

                // add value to pack
                if (pMark <= 1) pack[pMark] = cur
                pMark = (pMark + 1)
            }

            pack.joinToString("").toInt()
//                .also { println(it) }
        }
    }.also { println("Sum Max Joltage 2, $it") }

    part(2, input) {
        input.sumOf { inp ->
            val bank = inp.map { it.digitToInt() }
            val bSize = bank.size
            val pack = MutableList(12) { 0 }
            var pMark = 0

            for (i in 0 until bSize) {
                val cur = bank[i]
                val bankRemaining = bSize - 1 - i

                // check for lower value
                val iLowerNum = pack.indexOfFirst { it != 0 && it < cur }
                if (iLowerNum != -1) {
                    val remainingFromLower = 11 - iLowerNum
                    pMark = 11 - minOf(remainingFromLower, bankRemaining)
                    for (j in pMark..11) {
                        pack[j] = 0
                    }
                }

                // add value to pack
                if (pMark <= 11) pack[pMark] = cur
                pMark = (pMark + 1)
            }

            pack.joinToString("").toLong()
//                .also { println(it) }
        }
    }.also { println("Sum Max Joltage 12, $it") }
}
