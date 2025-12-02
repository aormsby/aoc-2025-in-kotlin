import kotlin.math.sqrt

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
        input.fold(0L) { acc, strRange ->
            val (n, m) = strRange.split('-')
            val r = n.toLong()..m.toLong()
            val iter = r.iterator()
            var next = acc

            while (iter.hasNext()) {
                val cur = iter.next()
                with(cur.toString()) {
                    if (length != 1) {
                        for (i in 1..sqrt(length.toFloat()).toInt()) {
                            if (length % i == 0) {
                                val other = length / i
                                if (chunked(i).distinct().size == 1
                                    || (other != length && chunked(other).distinct().size == 1)
                                ) {
                                    next += cur
                                    break
                                }
                            }
                        }
                    }
                }
            }
            next
        }
    }.also { println("Repeating IDs, $it") }
}
