package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day10.SyntaxChecker
import java.lang.IllegalArgumentException
import java.util.*


fun main() {

    val input: List<String> = InputHelper("day10.txt").readLines()

    println("Sum of invalid characters is ${input.sumOf { SyntaxChecker(it).checkInvalid() }}.")

    val missingSorted = input.map {
        SyntaxChecker(it).checkMissing()
    }.filter { it != 0L }.sorted()

    println("Middle score for missing characters is ${missingSorted[(missingSorted.size) / 2]}.")
}

object Day10 {

    class SyntaxChecker(private val input: String) {

        private val stack = LinkedList<Char>()

        fun checkMissing(): Long {
            if (checkInvalid() != 0) {
                return 0
            }

            var score = 0L

            for (char in stack) {
                score = score * 5 + scoreMissing(char)
            }

            return score
        }

        fun checkInvalid(): Int {
            for (char in input) {
                when (char) {
                    '(' -> stack.push(')')
                    '[' -> stack.push(']')
                    '<' -> stack.push('>')
                    '{' -> stack.push('}')
                    ')', ']', '>', '}' -> {
                        val last = stack.pop()
                        if (last != char) {
                            return scoreInvalid(char)
                        }
                    }
                }
            }

            return 0
        }

        private fun scoreInvalid(char: Char): Int {
            return when (char) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw IllegalArgumentException("Unexpected character $char.")
            }
        }

        private fun scoreMissing(char: Char): Int {
            return when (char) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw IllegalArgumentException("Unexpected character $char.")
            }
        }

    }

}