package days

import java.io.File

class Day02 : Day {
    override fun executePart1(): Long {
        var xPos = 0
        var yPos = 0

        val data = File("src/main/resources/day02-1.txt").useLines { it.toList().map { s ->
            val l = s.split(" ")

            Cmd(l[0], l[1].toInt())
        } }

        for (d in data) {
            when(d.direction) {
                "forward" -> xPos += d.x
                "down" -> yPos += d.x
                "up" -> yPos -= d.x
            }
        }

        return (xPos * yPos).toLong()
    }

    override fun executePart2(): Long {
        var xPos = 0
        var yPos = 0
        var aim = 0

        val data = File("src/main/resources/day02-2.txt").useLines { it.toList().map { s ->
            val l = s.split(" ")

            Cmd(l[0], l[1].toInt())
        } }

        for (d in data) {
            when(d.direction) {
                "forward" -> {
                    xPos += d.x
                    yPos += aim * d.x
                }
                "down" -> aim += d.x
                "up" -> aim -= d.x
            }
        }

        return (xPos * yPos).toLong()
    }

    data class Cmd(val direction: String, val x: Int)
}