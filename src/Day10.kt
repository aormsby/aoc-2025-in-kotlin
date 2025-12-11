import com.microsoft.z3.Context
import com.microsoft.z3.Status

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
        input.fold(0L) { acc, line ->
            var target = intArrayOf()
            val buttons = mutableListOf<IntArray>()

            line.split(' ').forEach { group ->
                if (group.first() == '(') {
                    buttons.add(
                        group.substring(1, group.length - 1)
                            .split(',')
                            .map { it.toInt() }
                            .toIntArray()
                    )
                } else if (group.first() == '{') {
                    target =
                        group.substring(1, group.length - 1)
                            .split(',')
                            .map { it.toInt() }
                            .toIntArray()
                }
            }

            Context().use { ctx ->
                val opt = ctx.mkOptimize()
                val vars = buttons.indices.map { ctx.mkIntConst("b$it") }
                vars.forEach { opt.Add(ctx.mkGe(it, ctx.mkInt(0))) }
                target.indices.forEach { i ->
                    val terms = buttons.withIndex().filter { i in it.value }.map { vars[it.index] }
                    if (terms.isNotEmpty()) {
                        val sum = if (terms.size == 1) terms[0]
                        else ctx.mkAdd(*terms.toTypedArray())
                        opt.Add(ctx.mkEq(sum, ctx.mkInt(target[i])))
                    } else if (target[i] != 0) throw RuntimeException("sigh")
                }
                opt.MkMinimize(ctx.mkAdd(*vars.toTypedArray()))
                if (opt.Check() == Status.SATISFIABLE) {
                    return@fold acc + vars.sumOf { opt.model.evaluate(it, false).toString().toInt() }
                } else 0
            }


            return@fold acc
        }
    }.also { println("Joltage - Sum Fewest Presses, $it") }
}