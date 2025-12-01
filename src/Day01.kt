import java.lang.Math.floorDiv

fun main() {
    day(1, "Secret Entrance")

    val input = readInput("Day01")
//    val input = readInput("Day01_test")

    part(1, input) {
        var numZero = 0
        var dial = 50

        input.forEach { i ->
            val dir = i.take(1)
            val turn = i.drop(1).toInt()

            when (dir) {
                "L" -> dial -= turn
                "R" -> dial += turn
            }

            dial = Math.floorMod(dial, 100)
            if (dial == 0)
                numZero++

//            println("$dir:$turn:$dial:$numZero")

        }

        numZero
    }.also { println("Hit 0, $it") }

    part(2, input) {
        var numZero = 0
        var dial = 50

        input.forEach { i ->
            val dir = i.take(1)
            val turn = i.drop(1).toInt()

            when (dir) {
                "L" -> {
                    if (dial == 0) numZero--
                    dial -= turn
                    numZero -= floorDiv(dial, 100) - if (dial % 100 == 0) 1 else 0
                }

                "R" -> {
                    dial += turn
                    numZero += floorDiv(dial, 100)
                }
            }

            dial = Math.floorMod(dial, 100)
//            println("$dir:$turn:$dial:$numZero")
        }

        numZero
    }.also { println("Passed 0, $it") }
}
