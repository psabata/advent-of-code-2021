package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day01.Sonar


fun main() {

    val sonar = Sonar(InputHelper("day01.txt").readIntLines())

    println("Depth increased ${sonar.depthIncrease()} times.")
    println("Depth increased ${sonar.depthIncrease(3)} times in sliding window of size 3.")

}

object Day01 {

    class Sonar(private val readouts: List<Int>) {

        fun depthIncrease(window : Int = 1): Int =
                readouts.dropLast(window).foldIndexed(0) { index, accumulator, _ ->
                    if (sumN(index, window) < sumN(index + 1, window)) accumulator + 1 else accumulator
                }

        private fun sumN(fromIndex: Int, n: Int): Int =
                readouts.subList(fromIndex, fromIndex + n).sum()

    }

}