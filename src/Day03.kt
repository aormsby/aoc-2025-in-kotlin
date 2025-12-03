fun main() {
    day(3, "Lobby")

    val input = readInput("Day03")
//    val input = readInput("Day03_test")

    part(1, input) {
        input.sumOf { strBank ->
            val intBank = strBank.map { it.digitToInt() }
            val first = intBank.dropLast(1).max()
            val second = intBank.drop(intBank.indexOf(first) + 1).max()
//            println("$first$second")
            ("$first$second").toInt()
        }
    }.also { println("Sum Max Joltage 2, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}
