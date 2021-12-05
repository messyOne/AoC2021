import days.*

fun main() {
    listOf(
        Day01(),
        Day02(),
        Day03(),
        Day04(),
        Day05()
    ).forEach {
        it.printHeader()
        println("Part 1: " + it.executePart1())
        println("Part 2: " + it.executePart2())
    }
}