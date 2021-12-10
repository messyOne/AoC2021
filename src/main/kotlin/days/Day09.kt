package days

import java.io.File

class Day09 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day09-1.txt").useLines {
            it.toList().map { s -> s.toList().map { c -> Character.getNumericValue(c) } }
        }

        val lowPoints: ArrayList<Int> = ArrayList()
        val yMax = data.size
        val xMax = data[0].size

        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                val c = data[y][x]
                val up = if (y - 1 >= 0) data[y - 1][x] else Int.MAX_VALUE
                val down = if (y + 1 < yMax) data[y + 1][x] else Int.MAX_VALUE
                val left = if (x - 1 >= 0) data[y][x - 1] else Int.MAX_VALUE
                val right = if (x + 1 < xMax) data[y][x + 1] else Int.MAX_VALUE

                if (listOf(up, down, left, right).all { c < it }) {
                    lowPoints.add(c)
                }
            }
        }

        return lowPoints.sumOf { it + 1 }.toLong()
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day09-2.txt").useLines {
            it.toList().map { s -> s.toList().map { c -> Character.getNumericValue(c) } }
        }

        val lowPoints: ArrayList<Pair<Int, Int>> = ArrayList()
        val yMax = data.size
        val xMax = data[0].size

        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                val c = data[y][x]
                val up = if (y - 1 >= 0) data[y - 1][x] else Int.MAX_VALUE
                val down = if (y + 1 < yMax) data[y + 1][x] else Int.MAX_VALUE
                val left = if (x - 1 >= 0) data[y][x - 1] else Int.MAX_VALUE
                val right = if (x + 1 < xMax) data[y][x + 1] else Int.MAX_VALUE

                if (listOf(up, down, left, right).all { c < it }) {
                    lowPoints.add(y to x)
                }
            }
        }

        val checked: MutableList<Pair<Int, Int>> = ArrayList<Pair<Int, Int>>().toMutableList()
        fun traverse(pos: Pair<Int, Int>, before: Int): Int {
            if (checked.contains(pos)) {
                return 0
            }

            val y = pos.first
            val x = pos.second

            if (x >= xMax || x < 0 || y >= yMax || y < 0) {
                return 0
            }

            val c = data[y][x]
            if (c == 9 || c <= before) {
                return 0
            }

            checked.add(pos)

            return 1 +
                    traverse(y - 1 to x, c) +
                    traverse(y + 1 to x, c) +
                    traverse(y to x + 1, c) +
                    traverse(y to x - 1, c)

        }

        val sizes = lowPoints.map { lowPoint ->
            traverse(lowPoint, -1)
        }.sorted().reversed()
        return sizes.take(3).fold(1) { acc: Int, i: Int -> acc * i }.toLong()
    }
}