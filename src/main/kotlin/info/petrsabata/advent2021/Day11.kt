package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day11.OctopusSimulator


fun main() {
    val input = InputHelper("day11.txt").readLines().map { line -> line.map { it.digitToInt() } }

    println("Flashes after 100 step: " + OctopusSimulator(input).step100())
    println("All octopuses flashed in step: " + OctopusSimulator(input).allFlash())
}

object Day11 {

    class OctopusSimulator(seed: List<List<Int>>) {

        private val school: List<List<Octopus>> = seed.mapIndexed { y, row ->
            row.mapIndexed { x, energy -> Octopus(Pair(x, y), energy) }
        }

        private val octopuses = school.flatten()

        private val width = school[0].size
        private val height = school.size

        var flashCounter = 0

        fun step100(): Int {
            for (step in 1..100) {
                step()
            }

            return flashCounter
        }

        fun allFlash(): Int {
            var step = 0

            do {
              step++
              step()
            } while (!octopuses.all { it.energyLevel == 0 })

            return step
        }

        private fun step() {
            octopuses.forEach {
                it.energyLevel++
                flash(it)
            }
            octopuses.forEach {
                it.reset()
            }
        }

        private fun flash(octopus: Octopus) {
            if (octopus.energyLevel <= 9 || octopus.flashed) {
                return
            }

            flashCounter++
            octopus.flashed = true

            for (neighbour in neighbours(octopus)) {
                neighbour.energyLevel++
                flash(neighbour)
            }
        }

        private fun neighbours(octopus: Octopus): List<Octopus> {
            val result = mutableListOf<Octopus>()

            for (dx in -1..1) {
                for (dy in -1..1) {
                    if (dx == 0 && dy == 0) {
                        continue
                    }

                    val x = octopus.x + dx
                    val y = octopus.y + dy

                    if (x < 0 || x >= width || y < 0 || y >= height) {
                        continue
                    }

                    result.add(school[y][x])
                }
            }

            return result
        }

    }

    class Octopus(
        position: Pair<Int, Int>,
        var energyLevel: Int,
        var flashed: Boolean = false,
    ) {

        fun reset() {
            if (flashed) {
                flashed = false
                energyLevel = 0
            }
        }

        val x = position.first
        val y = position.second
    }



}