package days

import java.io.File

class Day04 : Day {
    override fun executePart1(): Long {
        val data = File("src/main/resources/day04-1.txt").readLines()
        val draws = data.first().split(",").map { it.toInt() }

        val boards = data.drop(1)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { it -> Board(it.toList().map { s -> s.chunked(3).map { Field(it.trim().toInt()) } }) }

        draws.forEach { i ->
            boards.forEach { b ->
                b.call(i)
            }

            boards.forEach { b ->
                if (b.validate()) {
                    return i * b.remainingSum().toLong()
                }
            }
        }

        return 0
    }

    override fun executePart2(): Long {
        val data = File("src/main/resources/day04-2.txt").readLines()
        val draws = data.first().split(",").map { it.toInt() }

        val boards = data.drop(1)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { it -> Board(it.toList().map { s -> s.chunked(3).map { Field(it.trim().toInt()) } }) }

        draws.forEach { i ->
            val remainingBoards = boards.filter { !it.validate() }

            remainingBoards.forEach { b ->
                b.call(i)
            }

            remainingBoards.forEach { b ->
                if (b.validate() && remainingBoards.size == 1) {
                    return i * b.remainingSum().toLong()
                }
            }
        }

        return 0
    }

    data class Board(val columns: List<List<Field>>) {
        fun call(i: Int) {
            columns.forEach { c ->
                c.forEach { f ->
                    f.call(i)
                }
            }
        }

        fun validate(): Boolean {
            columns.forEach { c ->
                if (c.all { field -> field.isCalled }) {
                    return true
                }
            }

            for (i in 0 until columns[0].size) {
                if (columns.map { it[i] }.all { it.isCalled }) {
                    return true
                }
            }

            return false
        }

        fun remainingSum(): Int {
            return columns.fold(0) { result, current -> result + current.filter { !it.isCalled }.sumOf { it.number } }
        }
    }

    data class Field(val number: Int, var isCalled: Boolean = false) {
        fun call(i: Int) {
            if (!isCalled)
                isCalled = number == i
        }
    }
}
