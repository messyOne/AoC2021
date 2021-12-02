package days

interface Day {
    fun printHeader() {
        println("\nSolutions for " + javaClass.simpleName + "\n---------------------")
    }

    fun executePart1(): Long {
        return 0
    }

    fun executePart2(): Long {
        return 0
    }

}