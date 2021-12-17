package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day17.ProbeLauncher
import info.petrsabata.advent2021.Day17.TargetArea
import java.lang.Integer.max


fun main() {

    val targetArea = TargetArea(60..94, -171..-136)

    var maxHeight = 0
    var validLaunches = 0

    for (dx in 1..94) {
        for (dy in -171..1000) {
            ProbeLauncher(targetArea).launch(dx, dy).apply {
                if (this >= 0) {
                    maxHeight = max(maxHeight, this)
                    validLaunches++
                    // println("dx: $dx, dy: $dy, max height: $this")
                }
            }
        }
    }

    println("Max height: $maxHeight")
    println("Valid launches: $validLaunches")

}

object Day17 {

    class ProbeLauncher(private val targetArea: TargetArea) {

        private var x = 0
        private var y = 0

        private var maxY = 0

        fun launch(dx: Int, dy: Int): Int {
            x += dx
            y += dy

            maxY = max(maxY, y)

            if (targetArea.hit(x, y)) {
                return maxY
            }

            if (targetArea.miss(x, y)) {
                return -1
            }

            return launch(max(dx - 1, 0), dy - 1)
        }


    }

    class TargetArea(private val targetX: IntRange, private val targetY: IntRange) {

        fun hit(x: Int, y: Int): Boolean {
            return targetX.contains(x) && targetY.contains(y)
        }

        fun miss(x: Int, y: Int): Boolean {
            return (x > targetX.last) || (y < targetY.first)
        }
    }


}