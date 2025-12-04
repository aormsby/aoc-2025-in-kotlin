fun main() {
    day(4, "Printing Department")

    val input = array2DInput("Day04")
//    val input = array2DInput("Day04_test")

    part(1, input) {
        val numRows = input.size
        val rowLength = input[0].size
        var accessibleRolls = 0

        input.forEachIndexed { rowI, line ->
            val rowNeighbors = validNeighbors(numRows, rowLength, rowI = rowI)
            line.forEachIndexed { colI, char ->
                if (char == '@') {
                    val colNeighbors = validNeighbors(numRows, rowLength, colI = colI, startNeighbors = rowNeighbors)
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
    }.also { println("Quick Rolls Accessible, $it") }

    part(2, input) {
        val numRows = input.size
        val rowLength = input[0].size
        var curAccessibleRolls = 0
        var totalAccessibleRolls = 0

        do {
            curAccessibleRolls = 0
            input.forEachIndexed { rowI, line ->
                val rowNeighbors = validNeighbors(numRows, rowLength, rowI = rowI)
                line.forEachIndexed { colI, char ->
                    if (char == '@') {
                        val colNeighbors =
                            validNeighbors(numRows, rowLength, colI = colI, startNeighbors = rowNeighbors)
                        val adjRolls = colNeighbors.fold(0) { acc, coord ->
                            if (input[rowI + coord.first][colI + coord.second] == '@')
                                acc + 1
                            else acc
                        }
                        if (adjRolls < 4) {
                            input[rowI][colI] = '.'
                            curAccessibleRolls++
                        }
                    }
                }
            }
            totalAccessibleRolls += curAccessibleRolls
        } while (curAccessibleRolls != 0)

        totalAccessibleRolls
    }.also { println("Total Rolls Accessible, $it") }
}
