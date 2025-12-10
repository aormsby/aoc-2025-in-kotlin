fun main() {
    day(10, "Factory")

    val input = readLines("Day10")
//    val input = readLines("Day10_test")

    part(1, input) { input ->
        input.fold(0) { acc, line ->
            var lightMask = 0
            val buttonMasks = mutableListOf<Int>()

            line.split(' ').forEach { group ->
                if (group.first() == '(') {
                    buttonMasks.add(
                        bitmaskOf(
                            group.substring(1, group.length - 1)
                                .split(',')
                                .map { it.toInt() }
                        )
                    )
                } else if (group.first() == '[') {
                    lightMask = bitmaskOf(
                        group.substring(1, group.length - 1)
                            .mapIndexedNotNull { i, ch ->
                                if (ch == '#') i
                                else null
                            }
                    )
                }
            }
            check(lightMask != 0)

            // BFS BEGIN
            val masksSeen = HashSet<Int>()
            val q = ArrayDeque<Pair<Int, Int>>() // (mask, distance)
            var fewestPresses = -1

            // start by marking 0 as seen, kicks off the queue
            masksSeen.add(0)
            q.add(0 to 0)

            while (fewestPresses == -1 && q.isNotEmpty()) {
                val (mask, dist) = q.removeFirst()
                for (b in buttonMasks) {
                    val next = mask xor b
                    if (next == lightMask) {
                        fewestPresses = dist + 1
                        break
                    }
                    // if cur mask not seen, queue next in branch, else skip
                    if (masksSeen.add(next)) {
                        q.add(next to (dist + 1))
                    }
                }
            }

            acc + fewestPresses
        }
    }.also { println("Lights - Sum Fewest Presses, $it") }

    part(2, input) { input ->
        null
    }.also { println("Joltage - Sum Fewest Presses, $it") }
}