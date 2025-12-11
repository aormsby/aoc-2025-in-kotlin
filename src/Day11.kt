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

    part(2, input) { inp ->
        val outputMap = mutableMapOf<String, List<String>>()
        inp.forEach { line ->
            outputMap[line.substringBefore(':')] =
                line.substringAfter(": ").split(' ')
        }
        val memoPaths = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()

        fun dfs(curKey: String, curPath: ArrayDeque<String>, hasFFT: Boolean, hasDAC: Boolean): Long {
            if (curKey in curPath)
                return 0L

            val fft = hasFFT || curKey == "fft"
            val dac = hasDAC || curKey == "dac"

            //
            val memoKey = Triple(curKey, fft, dac)
            memoPaths[memoKey]?.let { numPaths ->
                return numPaths
            }

            curPath.addLast(curKey)
            var pathsOut = 0L
            val outs = outputMap[curKey] ?: emptyList()

            for (next in outs) {
                if (next == "out" && fft && dac) {
                    pathsOut += 1
                } else {
                    pathsOut += dfs(next, curPath, fft, dac)
                }
            }

            curPath.removeLast()
            memoPaths[memoKey] = pathsOut
            return pathsOut
        }

        dfs("svr", ArrayDeque(), hasFFT = false, hasDAC = false)
    }.also { println("FFT/DAC Paths OUT, $it") }
}