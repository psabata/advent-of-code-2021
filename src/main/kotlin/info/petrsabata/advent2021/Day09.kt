package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day09.FloorMap


fun main() {

    val input: List<List<Int>> = InputHelper("day09.txt").readLines().map { line -> line.map { it.digitToInt() } }

    val floorMap = FloorMap(input)

    println("Low points risk level: " + floorMap.lowPointsRisk())
    println("Largest basins score: " + floorMap.largestBasins())

}

object Day09 {

    class FloorMap(private val floor: List<List<Int>>) {
        private val width = floor[0].size
        private val height = floor.size

        fun lowPointsRisk(): Int {
            return lowPoints().sumOf { value(it) + 1 }
        }

        fun largestBasins(): Int {
            return basins().sortedBy { it.size }.takeLast(3).fold(1) { acc, basin -> acc * basin.size }
        }

        private fun basins(): List<Set<Pair<Int, Int>>> {
            return lowPoints().map { basin(it) }
        }

        private fun basin(point: Pair<Int, Int>): Set<Pair<Int, Int>> {
            val points = mutableSetOf(point)
            val currentValue = value(point)

            neighbours(point).forEach {
                val neighborValue = value(it)

                if ((neighborValue != 9) && (neighborValue > currentValue)) {
                    points.add(it)
                    points.addAll(basin(it))
                }
            }

            return points
        }

        private fun lowPoints(): MutableList<Pair<Int, Int>> {

            val lowPoints = mutableListOf<Pair<Int, Int>>()

            floor.forEachIndexed { y, row ->
                row.forEachIndexed { x, point ->
                    if (neighbours(Pair(x, y)).all { value(it) > point }) {
                        lowPoints.add(Pair(x, y))
                    }
                }
            }

            return lowPoints
        }

        private fun neighbours(point: Pair<Int, Int>): List<Pair<Int, Int>> {
            val neighbours = mutableListOf<Pair<Int, Int>>()

            val x = point.first
            val y = point.second

            if (x - 1 >= 0) neighbours.add(Pair(x - 1, y))
            if (x + 1 < width) neighbours.add(Pair(x + 1, y))
            if (y - 1 >= 0) neighbours.add(Pair(x, y - 1))
            if (y + 1 < height) neighbours.add(Pair(x, y + 1))

            return neighbours
        }

        private fun value(point: Pair<Int, Int>): Int {
            return floor[point.second][point.first]
        }
    }

}