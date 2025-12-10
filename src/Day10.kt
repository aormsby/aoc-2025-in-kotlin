fun main() {
    day(10, "Factory")

    val input = readLines("Day10")
//    val input = readLines("Day10_test")

    part(1, input) { input ->
        input.fold(0) { acc, line ->
            var lightMask: Int = -1
            val buttonMasks = mutableListOf<Int>()

            line.split(' ').forEach { group ->
                if (group.first() == '[') {
                    lightMask = bitmaskOf(
                        group.substring(1, group.length - 1)
                            .mapIndexedNotNull { i, ch ->
                                if (ch == '#') i
                                else null
                            })
                } else if (group.first() != '{') {
                    buttonMasks.add(
                        bitmaskOf(
                            group.substring(1, group.length - 1)
                                .split(',')
                                .map { it.toInt() }
                        )
                    )
                }
            }
            check(lightMask != -1)

            var smallestCombo = -1
            for (comboSize in 1..<buttonMasks.size) {
                val pointers = IntArray(comboSize) { it }

                while (true) {
                    val mask = (0 until comboSize).fold(0) { acc, i ->
                        acc xor buttonMasks[pointers[i]]
                    }

                    if (mask == lightMask) {
                        smallestCombo = comboSize
                        break
                    }

                    var cur = comboSize - 1
                    while (cur >= 0 && pointers[cur] == buttonMasks.size - comboSize + cur) cur--
                    if (cur < 0) break

                    pointers[cur]++
                    for (other in (cur + 1) until comboSize) {
                        pointers[other] = pointers[other - 1] + 1
                    }
                }

                if (smallestCombo != -1) {
                    break
                }
            }

            if (smallestCombo != -1)
                acc + smallestCombo
            else acc + buttonMasks.size
        }
    }.also { println("Lights - Sum Fewest Presses, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}