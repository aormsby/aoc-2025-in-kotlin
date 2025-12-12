fun main() {
    day(12, "Christmas Tree Farm")

    val input = splitInput("Day12", "\n\n")
//    val input = splitInput("Day12_test", "\n\n")

    data class TreeConfig(
        val width: Int,
        val length: Int,
        val placement: List<Int>,
    )

    data class Present(
        val shape: List<List<Boolean>>,
        val area: Int,
    )

    // LOL, fails sample, works for actual. What a lazy move, this puzzle!
    // Funny, because someone is surely trying to fit all the pieces. But also anticlimactic.
    part(1, input) { inp ->
        val presents = mutableMapOf<Int, Present>()
        val treeConfigs = mutableListOf<TreeConfig>()

        for (i in 0..<inp.size) {
            if (i == inp.size - 1) {
                inp[i].split("\n").forEach { tree ->
                    val (w, l) = tree.substringBefore(':').split('x').map { it.toInt() }
                    val arr = tree.substringAfter(": ").split(" ").map { it.toInt() }
                    treeConfigs.add(TreeConfig(w, l, arr))
                }
            } else {
                val pres = inp[i].split("\n")
                var area = 0
                val shape = pres.drop(1).map { line ->
                    line.map { ch ->
                        if (ch == '#') {
                            area++
                            true
                        } else false
                    }
                }

                presents[pres[0][0].digitToInt()] = Present(shape, area)
            }
        }

        treeConfigs.fold(0L) { tAcc, tree ->
            val treeArea = tree.width * tree.length
            val presentsArea = tree.placement.foldIndexed(0L) { i, pAcc, num ->
                pAcc + (num * presents[i]!!.area)
            }
            if (presentsArea <= treeArea) tAcc + 1
            else tAcc
        }
    }.also { println("Fits, $it") }

//    part(2, input) { inp ->
//        null
//    }.also { println("asdf, $it") }
}