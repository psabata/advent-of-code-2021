package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day02.Course.Direction
import info.petrsabata.advent2021.Day02.Submarine


fun main() {

    val input = InputHelper("day02.txt").readLines()

    println("Submarine destination: ${Submarine(input).navigate()}")
    println("Submarine aim: ${Submarine(input).aim()}")

}

object Day02 {

    class Submarine(input: List<String>) {

        private var x: Int = 0
        private var y: Int = 0
        private var aim: Int = 0

        private val course: List<Course> = input.map {
            Course(it.split(" "))
        }

        fun navigate(): Int {
            course.forEach {
                when (it.direction) {
                    Direction.FORWARD -> x += it.distance
                    Direction.UP -> y -= it.distance
                    Direction.DOWN -> y += it.distance
                }
            }

            return x * y
        }

        fun aim(): Int {
            course.forEach {
                when (it.direction) {
                    Direction.UP -> aim -= it.distance
                    Direction.DOWN -> aim += it.distance
                    Direction.FORWARD -> {
                        x += it.distance
                        y += it.distance * aim
                    }
                }
            }

            return x * y
        }

    }

    class Course(input: List<String>) {

        val direction: Direction
        val distance: Int

        init {
            direction = Direction.valueOf(input[0].uppercase())
            distance = input[1].toInt()
        }

        enum class Direction {
            FORWARD,
            DOWN,
            UP
        }


    }

}