package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day12.CaveMap


fun main() {

    val inputTest: List<String> = InputHelper("day12-test.txt").readLines()
    val input: List<String> = InputHelper("day12.txt").readLines()

    val caveMapTest = CaveMap(inputTest)
    check(caveMapTest.trace(false) == 10)
    check(caveMapTest.trace(true) == 36)

    val caveMap = CaveMap(input)
    println("There are ${caveMap.trace(false)} different cave paths.")
    println("There are ${caveMap.trace(true)} different cave paths (when entering single small cave twice.")

}

object Day12 {

    class CaveMap(seed: List<String>) {

        val map: Map<String, List<String>>
        var pathCounter = 0

        init {
            val mutableMap = mutableMapOf<String, MutableList<String>>()
            seed.forEach {
                val nodes = it.split('-')
                mutableMap.getOrPut(nodes[0]) { mutableListOf() }.add(nodes[1])
                mutableMap.getOrPut(nodes[1]) { mutableListOf() }.add(nodes[0])
            }
            map = mutableMap
        }

        fun trace(visitSingleSmallTwice: Boolean): Int {
            pathCounter = 0
            trace("start", mutableListOf("start"), !visitSingleSmallTwice)
            return pathCounter
        }

        private fun trace(node: String, visited: MutableList<String>, smallTwice: Boolean) {
            if (node == "end") {
                pathCounter++
                return
            }

            map[node]!!.forEach {
                if (!visited.contains(it) || it.isUppercase()) {
                    trace(it, visited.toMutableList().apply { add(it) }, smallTwice)
                }
                if (visited.contains(it) && it.isLowercase() && it != "start" && !smallTwice) {
                    trace(it, visited.toMutableList().apply { add(it) }, true)
                }
            }
        }

        private fun String.isUppercase(): Boolean =
            uppercase() == this

        private fun String.isLowercase(): Boolean =
            lowercase() == this

    }

}