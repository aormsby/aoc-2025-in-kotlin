fun main() {
    day(4, "Printing Department")

    val input = array2DInput("Day04")
//    val input = array2DInput("Day04_test")

    // todo: extract utils
    val crossNeighbors = listOf(
        -1 to 0, 1 to 0, 0 to -1, 0 to 1,
    )

    val diagNeighbors = listOf(
        -1 to 1, 1 to 1, 1 to -1, -1 to -1,
    )

    val allNeighbors = crossNeighbors + diagNeighbors

    fun validNeighbors(
        knownNeighbors: List<Pair<Int, Int>>?,
        row: Int?,
        col: Int?,
        numRows: Int,
        rowLength: Int
    ): List<Pair<Int, Int>> {
        var n = knownNeighbors ?: allNeighbors

        n = when (row) {
            0 -> n.filterNot { it.first == -1 }
            numRows - 1 -> n.filterNot { it.first == 1 }
            else -> n
        }

        n = when (col) {
            0 -> n.filterNot { it.second == -1 }
            rowLength - 1 -> n.filterNot { it.second == 1 }
            else -> n
        }

        return n
    }

    part(1, input) {
        val numRows = input.size
        val rowLength = input[0].size
        var accessibleRolls = 0

        input.forEachIndexed { rowI, line ->
            val rowNeighbors = validNeighbors(null, rowI, null, numRows, rowLength)
            line.forEachIndexed { colI, char ->
                if (char == '@') {
                    val colNeighbors = validNeighbors(rowNeighbors, null, colI, numRows, rowLength)
                    val adjRolls = colNeighbors.fold(0) { acc, coord ->
                        if (input[rowI + coord.first][colI + coord.second] == '@')
                            acc + 1
                        else acc
                    }
                    if (adjRolls < 4) accessibleRolls++
                }
            }
        }

        accessibleRolls
    }.also { println("Rolls Accessible, $it") }

    part(2, input) {
        null
    }.also { println("TBD, $it") }
}
