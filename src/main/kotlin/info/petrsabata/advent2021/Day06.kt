package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day06.FishSimulator
import java.util.*

fun main() {

    val input = InputHelper("day06.txt").readText().split(',').map { it.toInt() }

    println("There will be ${FishSimulator(input).run(80)} lanternfishes after 80 days.")
    println("There will be ${FishSimulator(input).run(256)} lanternfishes after 256 days.")

}

object Day06 {

    class FishSimulator(seed: List<Int>) {

        var state: SortedMap<Int, Long> = seed.groupBy { it }.mapValues { it.value.size.toLong() }.toSortedMap()

        fun run(days: Int): Long {
            for (i in 1..days) {
                run()
            }

            return state.values.sum()
        }

        private fun run() {
            val result = TreeMap<Int, Long>()

            state.entries.forEach { (timer, count) ->
                if (timer > 0) {
                    result[timer - 1] = result.getOrPut(timer - 1) { 0 } + count
                } else {
                    result[6] = result.getOrPut(6) { 0 } + count
                    result[8] = count
                }
            }

            state = result
        }

    }

}