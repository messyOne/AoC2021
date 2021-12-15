package days

import java.io.File

class Day13 : Day {
    override fun executePart1(): Long {
        val coords = HashSet<Point>()
        val introductions = ArrayList<Fold>()

        File("src/main/resources/day13-1.txt").readLines().forEach { line ->
            if (line.contains(',')) {
                val split = line.split(',')
                val x = split[0].toInt()
                val y = split[1].toInt()

                coords.add(Point(x, y))
            }

            if (line.contains('=')) {
                val split = line.split('=')
                val dir = split[0].last()
                val value = split[1].toInt()

                introductions.add(Fold(dir, value))
            }
        }

        val result = exe(coords, listOf(introductions.first()))

        return result.size.toLong()
    }

    private fun exe(coords: HashSet<Point>, introductions: List<Fold>): HashSet<Point> {
        if (introductions.isEmpty()) {
            return coords
        }

        val newCoords = HashSet<Point>()
        val intro = introductions.first()

        when (intro.dir) {
            'y' -> {
                val pair = coords.partition { it.y > intro.value }

                newCoords.addAll(pair.second)
                pair.first.forEach { it.y = intro.value * 2 - it.y }
                newCoords.addAll(pair.first)
            }
            'x' -> {
                val pair = coords.partition { it.x > intro.value }

                newCoords.addAll(pair.second)
                pair.first.forEach { it.x = intro.value * 2 - it.x }
                newCoords.addAll(pair.first)
            }
        }

        return exe(newCoords, introductions - intro)
    }

    data class Point(var x: Int, var y: Int)
    data class Fold(val dir: Char, val value: Int)

    override fun executePart2(): Long {
        val coords = HashSet<Point>()
        val introductions = ArrayList<Fold>()

        File("src/main/resources/day13-2.txt").readLines().forEach { line ->
            if (line.contains(',')) {
                val split = line.split(',')
                val x = split[0].toInt()
                val y = split[1].toInt()

                coords.add(Point(x, y))
            }

            if (line.contains('=')) {
                val split = line.split('=')
                val dir = split[0].last()
                val value = split[1].toInt()

                introductions.add(Fold(dir, value))
            }
        }

        val result = exe(coords, introductions)
        val xMax = result.maxOf { it.x }
        val yMax = result.maxOf { it.y }

        for (y in 0..yMax) {
            println()
            for (x in 0..xMax) {
                val value = result.find { point -> point.x == x && point.y == y }

                if (value == null) {
                    print('.')
                } else {
                    print('#')
                }
            }
        }
        println()

        return result.size.toLong()
    }
}