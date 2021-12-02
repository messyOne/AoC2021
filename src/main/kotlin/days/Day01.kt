package days

import java.io.File

class Day01 : Day {
    override fun executePart1(): Long {

        val data = File("src/main/resources/day01-1.txt").useLines { it.toList().map { s -> s.toInt() } }

        var counter = 0
        var tmp = data[0]
        for (x in data.takeLast(data.size-1)) {
            if (x > tmp) {
                counter++
            }
            tmp = x
        }

        return counter.toLong()
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day01-2.txt").useLines { it.toList().map { s -> s.toInt() } }

        var counter = 0
        var tmp = (data[0] + data[1] + data[2])

        for (i in 1 until data.size) {
            if (i+2 >= data.size) {
                return counter.toLong()
            }

            val n = data[i] + data[i+1] + data[i+2]

            if (n > tmp) {
                counter++
            }

            tmp = n
        }

        return counter.toLong()
    }
}