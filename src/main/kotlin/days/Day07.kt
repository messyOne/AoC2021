package days

import java.io.File
import kotlin.math.abs

class Day07 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day07-1.txt").useLines {
            it.toList().flatMap { s -> s.split(",").map(String::toInt) }
        }

        var total = 0L
        val avg = data.sorted()[data.size/2]

        for (x in data) {
            total += abs(x - avg)
        }

        return total
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day07-2.txt").useLines {
            it.toList().flatMap { s -> s.split(",").map(String::toInt) }
        }

        var minCosts = Long.MAX_VALUE
        val mem = HashMap<Int, Int>()

        for (x in data.minOrNull()!!..data.maxOrNull()!!) {
            var total = 0L

            for (y in data) {
                if (x != y) {
                    val d = abs(x - y)

                    if (mem.containsKey(d)) {
                        total += mem[d]!!
                    } else {
                        val sum = (1..d).fold(0) { acc: Int, i: Int -> acc + i }
                        total += sum
                        mem[d] = sum
                    }
                }
            }

            if (total < minCosts) {
                minCosts = total
            }
        }

        return minCosts
    }
}