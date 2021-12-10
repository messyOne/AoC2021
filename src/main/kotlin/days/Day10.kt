package days

import java.io.File

class Day10 : Day {
    override fun executePart1(): Long {
        val corrupted = File("src/main/resources/day10-1.txt").readLines().sumOf { line ->
            val brackets = mapOf(')' to '(', '}' to '{', ']' to '[', '>' to '<')
            val opener = ArrayList<Char>()

            val map = line.toList().map { char ->
                if (brackets.values.contains(char)) {
                    opener.add(char)
                    0
                } else {
                    val last = opener.removeLast()
                    if (last != brackets[char]) {
                        when (char) {
                            ')' -> 3
                            ']' -> 57
                            '}' -> 1197
                            '>' -> 25137
                            else -> 0
                        }
                    } else 0
                }
            }

            map.sum()
        }

        return corrupted.toLong()
    }

    override fun executePart2(): Long {
        val brackets = mapOf(')' to '(', '}' to '{', ']' to '[', '>' to '<')

        val data = File("src/main/resources/day10-2.txt").readLines().filter { line ->
            val opener = ArrayList<Char>()

            val map = line.toList().map { char ->
                if (brackets.values.contains(char)) {
                    opener.add(char)
                    0
                } else {
                    val last = opener.removeLast()
                    if (last != brackets[char]) {
                        when (char) {
                            ')' -> 3
                            ']' -> 57
                            '}' -> 1197
                            '>' -> 25137
                            else -> 0
                        }
                    } else 0
                }
            }

            map.sum() == 0
        }

        val scores = data.map { line ->
            var total = 0L
            val opener = ArrayList<Char>()

            line.toList().forEach { char ->
                if (brackets.values.contains(char)) {
                    opener.add(char)
                } else {
                    opener.removeLast()
                }
            }

            opener.reversed().forEach { c ->
                when (brackets.filterValues { it == c }.keys.first()) {
                    ')' -> total = total * 5 + 1
                    ']' -> total = total * 5 + 2
                    '}' -> total = total * 5 + 3
                    '>' -> total = total * 5 + 4
                }
            }

            total
        }

        return scores.sorted()[scores.size / 2]
    }
}