package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day05.Line

fun main() {

    val lines = InputHelper("day05.txt").readLines().map { Line(it) }

    println("Dangerous orthogonal: " + lines.flatMap { it.getOrthogonalPoints() }.groupBy { it }.count { it.value.size > 1 })
    println("Dangerous all: " + lines.flatMap { it.getPoints() }.groupBy { it }.count { it.value.size > 1 })
}

object Day05 {

    class Line(entry: String) {

        private companion object {
            val entryPattern = Regex("""^(\d+),(\d+) -> (\d+),(\d+)$""")
        }

        private val start: Point
        private val end: Point

        init {
            val entryValues = entryPattern.matchEntire(entry)?.groupValues
                ?: throw IllegalArgumentException("Invalid line entry: `$entry`.")

            start = Point(entryValues[1].toInt(), entryValues[2].toInt())
            end = Point(entryValues[3].toInt(), entryValues[4].toInt())
        }

        fun getPoints(): List<Point> {
            return getOrthogonalPoints().ifEmpty { getDiagonalPoints() }
        }

        fun getOrthogonalPoints(): List<Point> {
            if (start.x == end.x) {
                return expand(start.y, end.y).map {
                    Point(start.x, it)
                }
            }

            if (start.y == end.y) {
                return expand(start.x, end.x).map {
                    Point(it, start.y)
                }
            }

            return listOf()
        }

        private fun getDiagonalPoints(): List<Point> {
            val xRange = expand(start.x, end.x)
            val yRange = expand(start.y, end.y)

            return xRange.zip(yRange).map { (x, y) -> Point(x, y) }
        }

        private fun expand(a: Int, b: Int): IntProgression {
            return if (a < b) a..b else a downTo b
        }

    }

    data class Point(val x: Int, val y: Int)

}