fun main() {
    day(2, "TBD")

    val input = splitInput("Day02", ",")
//    val input = splitInput("Day02_test", ",")

    part(1, input) {
        input.fold(0L) { acc, strRange ->
            var localSum = 0L
            val (n, m) = strRange
                .split('-')
                .map { it.toLong() }

            for (cur in n..m) {
                val s = cur.toString()
                if (s.length == 1 || s.length % 2 != 0) {
                    continue
                }
                val half = s.length / 2
                var matches = true
                for (i in 0 until half) {
                    if (s[i] != s[i + half]) {
                        matches = false
                        break
                    }
                }
                if (matches) localSum += cur
            }
            acc + localSum
        }
    }.also { println("Mirrored IDs, $it") }



    part(2, input) {
        input.fold(0L) { acc, strRange ->
            var localSum = 0L
            val (n, m) = strRange
                .split('-')
                .map { it.toLong() }

            for (cur in n..m) {
                val s = cur.toString()
                val pattern = Regex("^(.+?)\\1+$")
                if (pattern.matches(s)) {
                    localSum += cur
                }
            }

            acc + localSum
        }
    }.also { println("Repeating IDs, $it") }
}
