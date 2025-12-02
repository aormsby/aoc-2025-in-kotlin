fun main() {
    day(2, "TBD")

    val input = splitInput("Day02", ",")
//    val input = splitInput("Day02_test", ",")

    part(1, input) {
        input.fold(0L) { acc, strRange ->
            val (n, m) = strRange.split('-')
            val r = n.toLong()..m.toLong()
            val iter = r.iterator()
            var next = acc

            while (iter.hasNext()) {
                val cur = iter.next()
                with(cur.toString()) {
                    if (length % 2 == 0 && substring(0, length / 2) == substring(length / 2, length)) {
                        next += cur
                    }
                }
            }
            next
        }
    }.also { println("Mirrored IDs, $it") }

    part(2, input) {
        2
    }.also { println("Repeating IDs, $it") }
}
