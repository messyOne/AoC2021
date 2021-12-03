import days.Day01
import days.Day02
import days.Day03

fun main() {
    listOf(
        Day01(),
        Day02(),
        Day03()
    ).forEach {
        it.printHeader()
        println("Part 1: " + it.executePart1())
        println("Part 2: " + it.executePart2())
    }
}