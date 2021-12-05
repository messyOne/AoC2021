package days

import java.io.File

class Day05 : Day {

    override fun executePart1(): Long {
        val coords: MutableMap<Int, MutableMap<Int, Int>> = HashMap()
        File("src/main/resources/day05-1.txt")
            .readLines()
            .map {
                val p = it.split("->")
                val x1 = p[0].split(",")[0].trim().toInt()
                val y1 = p[0].split(",")[1].trim().toInt()
                val x2 = p[1].split(",")[0].trim().toInt()
                val y2 = p[1].split(",")[1].trim().toInt()

                Line(x1, y1, x2, y2)
            }
            .filter { line -> line.x1 == line.x2 || line.y1 == line.y2 }
            .forEach { line ->
                markCoords(coords, line)
            }

//        for (y in 0..9) {
//            println()
//            for (x in 0..9) {
//                val i = coords.getOrDefault(y, HashMap()).getOrDefault(x, -1)
//                print(if (i == -1) '.' else i)
//            }
//        }

        return coords.flatMap { entry -> entry.value.map { e -> e.value } }.count { i -> i > 1 }.toLong()
    }

    override fun executePart2(): Long {
        val coords: MutableMap<Int, MutableMap<Int, Int>> = HashMap()
        File("src/main/resources/day05-2.txt")
            .readLines()
            .map {
                val p = it.split("->")
                val x1 = p[0].split(",")[0].trim().toInt()
                val y1 = p[0].split(",")[1].trim().toInt()
                val x2 = p[1].split(",")[0].trim().toInt()
                val y2 = p[1].split(",")[1].trim().toInt()

                Line(x1, y1, x2, y2)
            }
            .forEach { line ->
                markCoords(coords, line)
            }

        return coords.flatMap { entry -> entry.value.map { e -> e.value } }.count { i -> i > 1 }.toLong()
    }

    private fun markCoords(
        coords: MutableMap<Int, MutableMap<Int, Int>>,
        line: Line
    ) {
        fun mark(l: Line) {
            val col = coords.getOrDefault(l.y1, HashMap())
            val f = col.getOrDefault(l.x1, 0)
            col[l.x1] = f + 1
            coords[l.y1] = col
        }

        fun step(l: Line): Line {
            fun next(a: Int, b: Int): Int {
                return if (a > b) {
                    a - 1
                } else if (a < b) {
                    a + 1
                } else {
                    a
                }
            }

            mark(l)

            val step = Line(next(l.x1, l.x2), next(l.y1, l.y2), l.x2, l.y2)

            return if (step.isPoint()) {
                mark(step)

                step
            } else {
                step(step)
            }
        }

        step(line)
    }

    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun isPoint(): Boolean {
            return x1 == x2 && y1 == y2
        }

    }
}