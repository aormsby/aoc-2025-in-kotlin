fun main() {
    day(9, "Movie Theater")

    val input = readLines("Day09")
//    val input = readLines("Day09_test")

    part(1, input) {
        val coords = input.map { line ->
            val (x, y) = line.split(',').map { it.toLong() }
            Pair(x, y)
        }

        val areaList = mutableListOf<Triple<Long, Int, Int>>()

        for (i in 0..<coords.size) {
            for (j in (i + 1)..<coords.size) {
                val area = rectArea(coords[i], coords[j])
                areaList.add(Triple(area, i, j))
            }
        }
        
        areaList.maxOf { it.first }
    }.also { println("Largest Area, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}