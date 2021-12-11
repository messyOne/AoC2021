package days

import java.io.File

class Day11 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day11-1.txt").readLines()
            .map { s -> s.toList().map { Octopus(Character.getNumericValue(it)) } }
        var flashes = 0L

        fun traverse(y: Int, x: Int) {
            val octopus = data[y][x]
            octopus.inc()

            val up = y - 1 to x
            val down = y + 1 to x
            val left = y to x - 1
            val right = y to x + 1
            val dia1 = up.first to left.second
            val dia2 = up.first to right.second
            val dia3 = down.first to left.second
            val dia4 = down.first to right.second

            if (octopus.canFlash()) {
                octopus.flash()
                flashes++

                listOf(up, down, left, right, dia1, dia2, dia3, dia4).forEach {
                    if (it.first >= 0 && it.first < data.size && it.second >= 0 && it.second < data[0].size) {
                        traverse(it.first, it.second)
                    }
                }
            }
        }

        for (i in 1..100) {
            data.forEach { cols ->
                cols.forEach { octopus ->
                    octopus.inc()
                }
            }

            while (data.any { it.any { octopus -> octopus.canFlash() } }) {
                data.forEachIndexed { y, cols ->
                    cols.forEachIndexed { x, octopus ->
                        if (octopus.canFlash()) {
                            traverse(y, x)
                        }
                    }
                }
            }

            data.forEach { cols ->
                cols.forEach { octopus ->
                    octopus.reset()
                }
            }
        }

        return flashes
    }

    data class Octopus(var energy: Int, var hasFlashed: Boolean = false) {
        fun inc() {
            energy++
        }

        fun canFlash(): Boolean {
            return !hasFlashed && energy > 9
        }

        fun flash() {
            hasFlashed = true
        }

        fun reset() {
            if (energy > 9) {
                energy = 0
            }

            hasFlashed = false
        }
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day11-2.txt").readLines()
            .map { s -> s.toList().map { Octopus(Character.getNumericValue(it)) } }
        var flashesInTurn = 0L

        fun traverse(y: Int, x: Int) {
            val octopus = data[y][x]
            octopus.inc()

            val up = y - 1 to x
            val down = y + 1 to x
            val left = y to x - 1
            val right = y to x + 1
            val dia1 = up.first to left.second
            val dia2 = up.first to right.second
            val dia3 = down.first to left.second
            val dia4 = down.first to right.second

            if (octopus.canFlash()) {
                octopus.flash()
                flashesInTurn++

                listOf(up, down, left, right, dia1, dia2, dia3, dia4).forEach {
                    if (it.first >= 0 && it.first < data.size && it.second >= 0 && it.second < data[0].size) {
                        traverse(it.first, it.second)
                    }
                }
            }
        }

        for (i in 1..Int.MAX_VALUE) {
            flashesInTurn = 0

            data.forEach { cols ->
                cols.forEach { octopus ->
                    octopus.inc()
                }
            }

            while (data.any { it.any { octopus -> octopus.canFlash() } }) {
                data.forEachIndexed { y, cols ->
                    cols.forEachIndexed { x, octopus ->
                        if (octopus.canFlash()) {
                            traverse(y, x)
                        }
                    }
                }
            }

            data.forEach { cols ->
                cols.forEach { octopus ->
                    octopus.reset()
                }
            }

            if (flashesInTurn == (data.size * data[0].size).toLong()) {
                return i.toLong()
            }
        }

        return 0
    }
}