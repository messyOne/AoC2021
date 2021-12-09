package days

import java.io.File

class Day08 : Day {

    override fun executePart1(): Long {
        return File("src/main/resources/day08-1.txt").readLines().sumOf { line ->
            val output = line.split("|")[1]

            output.split(" ").map { it.trim() }.count { s -> s.length in 2..4 || s.length == 7 }
        }.toLong()
    }

    override fun executePart2(): Long {
        return File("src/main/resources/day08-2.txt").readLines().sumOf { line ->
            val input = line.split("|")[0]
            val output = line.split("|")[1]
            val digits = input.split(" ")
            val display = Display()
            val allChars = "acedgfb"

            val length6 = (allChars.toList() - digits.filter { it.length == 6 }
                .fold(allChars) { acc, s -> s.toList().intersect(acc.toList().toSet()).toSet().joinToString("") }
                .toList()
                .toSet()).joinToString("")
            display.markSafe(Display.Pos.HOR2, length6)
            display.markSafe(Display.Pos.VER3, length6)
            display.markSafe(Display.Pos.VER2, length6)

            val length5 = (allChars.toList() - digits.filter { it.length == 5 }
                .fold(allChars) { acc, s -> s.toList().intersect(acc.toList().toSet()).toSet().joinToString("") }
                .toList()
                .toSet()).joinToString("")
            display.markSafe(Display.Pos.VER1, length5)
            display.markSafe(Display.Pos.VER2, length5)
            display.markSafe(Display.Pos.VER3, length5)
            display.markSafe(Display.Pos.VER4, length5)

            digits.filter { it.length == 8 }.forEach { display.mark8(it) }
            digits.filter { it.length == 3 }.forEach { display.mark7(it) }
            digits.filter { it.length == 2 }.forEach { display.mark1(it) }
            digits.filter { it.length == 4 }.forEach { display.mark4(it) }

            display.resolve()

            output.trim().split(" ").map { c -> display.getNumber(c.trim()) }.joinToString("").toInt()
        }.toLong()
    }

    class Display() {
        private var positions = HashMap<Pos, String>()

        fun markSafe(pos: Pos, newChars: String): Boolean {
            val chars = positions.getOrDefault(pos, "acedgfb")

            positions[pos] = chars.toList().intersect(newChars.toList().toSet()).joinToString("")

            return true
        }

        fun mark8(chars: String) {
            Pos.values().forEach { markSafe(it, chars) }
        }

        fun mark7(chars: String) {
            markSafe(Pos.HOR1, chars)
            markSafe(Pos.VER2, chars)
            markSafe(Pos.VER4, chars)
        }

        fun mark1(chars: String) {
            markSafe(Pos.VER2, chars)
            markSafe(Pos.VER4, chars)
        }

        fun mark4(chars: String) {
            markSafe(Pos.VER1, chars)
            markSafe(Pos.VER2, chars)
            markSafe(Pos.HOR2, chars)
            markSafe(Pos.VER4, chars)
        }

        fun resolve() {
            val positions = traverse(positions, "")
            val chars = positions.values.joinToString("")

            Pos.values().forEach { pos ->
                if (!positions.containsKey(pos)) {
                    val v = "acedgfb".toList().filter { !chars.contains(it) }

                    positions[pos] = v.joinToString("")
                }
            }

            this.positions = positions
        }

        fun traverse(positions: HashMap<Pos, String>, hasChecked: String): HashMap<Pos, String> {
            if (positions.all { entry -> entry.value.length == 1 }) {
                return positions
            }

            val d = positions.values.find { it.length == 1 && !hasChecked.contains(it) }!!
            val newPositions = HashMap<Pos, String>()

            positions.forEach { entry ->
                if (entry.value.length > 1) {
                    newPositions[entry.key] = entry.value.toList().filter { it != d[0] }.joinToString("")
                } else {
                    newPositions[entry.key] = entry.value
                }
            }

            return traverse(newPositions, hasChecked + d)
        }

        fun getNumber(s: String): String {
            val positions =
                s.map { c -> positions.entries.find { entry -> entry.value[0] == c }!! }.map { entry -> entry.key }
            var number = ""

            if (positions.size == 6 && positions.containsAll(
                    listOf(
                        Pos.VER1,
                        Pos.VER2,
                        Pos.VER3,
                        Pos.VER4,
                        Pos.HOR1,
                        Pos.HOR3
                    )
                )
            ) {
                number += "0"
            }

            if (positions.size == 2 && positions.containsAll(listOf(Pos.VER2, Pos.VER4))) {
                number += "1"
            }

            if (positions.size == 5 && positions.containsAll(
                    Pos.values().filter { pos -> pos != Pos.VER1 && pos != Pos.VER4 })
            ) {
                number += "2"
            }

            if (positions.size == 5 && positions.containsAll(
                    Pos.values().filter { pos -> pos != Pos.VER1 && pos != Pos.VER3 })
            ) {
                number += "3"
            }

            if (positions.size == 4 && positions.containsAll(listOf(Pos.VER1, Pos.VER2, Pos.VER4, Pos.HOR2))) {
                number += "4"
            }

            if (positions.size == 5 && positions.containsAll(
                    Pos.values().filter { pos -> pos != Pos.VER2 && pos != Pos.VER3 })
            ) {
                number += "5"
            }

            if (positions.size == 6 && positions.containsAll(Pos.values().filter { pos -> pos != Pos.VER2 })) {
                number += "6"
            }

            if (positions.size == 3 && positions.containsAll(listOf(Pos.HOR1, Pos.VER2, Pos.VER4))) {
                number += "7"
            }

            if (positions.size == 7) {
                number += "8"
            }

            if (positions.size == 6 && positions.containsAll(Pos.values().filter { pos -> pos != Pos.VER3 })) {
                number += "9"
            }

            return number
        }

        enum class Pos {
            HOR1,
            HOR2,
            HOR3,
            VER1,
            VER2,
            VER3,
            VER4
        }
    }
}