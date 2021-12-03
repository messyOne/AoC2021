package days

import java.io.File
import kotlin.math.ceil

class Day03 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day03-1.txt").useLines { it.toList().map { s -> s } }

        var count0: Int
        var count1: Int
        var gamma = ""
        var epsilon = ""

        for (i in 0 until data[0].length) {
            count0 = 0
            count1 = 0

            for (d in data) {
                when(d[i]) {
                    '0' -> count0++
                    '1' -> count1++
                }
            }

            if (count0 > count1) {
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }

        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2).toLong()
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day03-2.txt").useLines { it.toList().map { s -> s } }

        val oxygen = traverseOxygen(data, 0)
        val scrubber = traverseScrubber(data, 0)

        return Integer.parseInt(oxygen, 2) * Integer.parseInt(scrubber, 2).toLong()
    }

    private fun traverseOxygen(data: List<String>, i: Int): String {
        if (data.size == 1) {
            return data[0]
        }

        val total1 = data.fold(0) { result, current -> result + (if (current[i] == '1') 1 else 0) }

        return if (total1 >= ceil(data.size/2.0)) {
            traverseOxygen(data.filter { s -> s[i] == '1' }, i+1)
        } else {
            traverseOxygen(data.filter { s -> s[i] == '0' }, i+1)
        }
    }

    private fun traverseScrubber(data: List<String>, i: Int): String {
        if (data.size == 1) {
            return data[0]
        }

        val total1 = data.fold(0) { result, current -> result + (if (current[i] == '1') 1 else 0) }

        return if (total1 < ceil(data.size/2.0)) {
            traverseScrubber(data.filter { s -> s[i] == '1' }, i+1)
        } else {
            traverseScrubber(data.filter { s -> s[i] == '0' }, i+1)
        }
    }
}