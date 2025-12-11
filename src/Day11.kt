fun main() {
    day(11, "Reactor")

    val input = readLines("Day11")
//    val input = readLines("Day11_test")

    part(1, input) { inp ->
        val outputMap = mutableMapOf<String, List<String>>()
        inp.forEach { line ->
            outputMap[line.substringBefore(':')] =
                line.substringAfter(": ").split(' ')
        }

        fun dfs(curKey: String): Int {
            var pathsOut = 0
            if (outputMap[curKey]?.first() == "out") {
                pathsOut = 1
            } else {
                outputMap[curKey]?.forEach { nextKey ->
                    pathsOut += dfs(nextKey)
                }
            }

            return pathsOut
        }

        dfs("you")
    }.also { println("Paths OUT, $it") }

    part(2, input) { input ->
        null
    }.also { println("asdf, $it") }
}