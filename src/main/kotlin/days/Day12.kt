package days

import java.io.File

class Day12 : Day {
    override fun executePart1(): Long {
        val nodes = HashSet<Node>()
        File("src/main/resources/day12-1.txt").readLines().forEach { s ->
            val parts = s.split('-')
            val l = parts[0]
            val r = parts[1]

            var nodeL = nodes.find { node -> node.id == l }
            var nodeR = nodes.find { node -> node.id == r }

            if (nodeL == null) {
                nodeL = Node(l)
            }

            if (nodeR == null) {
                nodeR = Node(r)
            }

            nodes.add(nodeL)
            nodes.add(nodeR)

            nodes.find { node -> node.id == l }!!.addNext(nodeR)
            nodes.find { node -> node.id == r }!!.addNext(nodeL)
        }

        var paths = 0

        fun traverse(node: Node, path: List<String>) {
            if (node.isEnd()) {
                paths++

                return
            }

            if (node.isSmall() && path.contains(node.id)) {
                return
            }


            node.nexts.map {
                traverse(it, path + node.id)
            }
        }

        traverse(nodes.find { it.isStart() }!!, ArrayList())

        return paths.toLong()
    }

    data class Node(val id: String) {
        fun addNext(next: Node) {
            if (nexts.none { it.id == next.id }) {
                nexts.add(next)
            }
        }

        val nexts = ArrayList<Node>()

        fun isSmall(): Boolean {
            return id[0].isLowerCase()
        }

        fun isEnd(): Boolean {
            return id == "end"
        }

        fun isStart(): Boolean {
            return id == "start"
        }
    }

}