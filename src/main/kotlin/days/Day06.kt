package days

import java.io.File
import java.util.*

class Day06 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day06-2.txt").useLines {
            it.toList().flatMap { s -> s.split(",").map(String::toInt) }
        }

        fun traverse(states: List<Int>, c: Int, until: Int): List<Int> {
            var counter = 0

            val newStates = states.map { state ->
                if (state == 0) {
                    counter++
                    6
                } else {
                    state - 1
                }
            } + Collections.nCopies(counter, 8)

            return if (c == until) {
                newStates
            } else {
                traverse(newStates, c + 1, until)
            }
        }

        return traverse(data, 1, 80).size.toLong()
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day06-2.txt").useLines {
            it.toList().flatMap { s -> s.split(",").map(String::toInt) }
        }

        val states = LongArray(9)

        for (d in data) {
            states[d]++
        }

        for (i in 1..256) {
            val mem = states[0]

            states[0] = states[1]
            states[1] = states[2]
            states[2] = states[3]
            states[3] = states[4]
            states[4] = states[5]
            states[5] = states[6]
            states[6] = states[7] + mem
            states[7] = states[8]
            states[8] = mem
        }

        return states.sum()
    }
}