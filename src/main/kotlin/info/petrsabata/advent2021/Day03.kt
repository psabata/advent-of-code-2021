package info.petrsabata.advent2021

import info.petrsabata.advent2021.Day03.DiagnosticReport
import info.petrsabata.advent2021.Day03.binaryToDecimal
import info.petrsabata.advent2021.Day03.inverseBits
import kotlin.math.pow


fun main() {
    val input = InputHelper("day03.txt").readLines()

    val diagnosticReport = DiagnosticReport(input)

    val gamma = diagnosticReport.gammaRate()
    val epsilon = gamma.inverseBits()

    println("For gamma rate $gamma and epsilon rate $epsilon the power consumption is ${gamma.binaryToDecimal() * epsilon.binaryToDecimal()}")


    val oxygen = diagnosticReport.oxygenRating()
    val co2 = diagnosticReport.co2Rating()

    println("For oxygen rating $oxygen and CO2 rating $co2 the life support rating is ${oxygen.binaryToDecimal() * co2.binaryToDecimal()}")
}

object Day03 {

    class DiagnosticReport(private val readouts: List<String>) {

        fun gammaRate(): String {
            val counter: MutableMap<Int, BitCounter> = mutableMapOf()

            readouts[0].indices.forEach {
                counter[it] = count(readouts, it)
            }

            return counter.values.fold("") { acc, bitCounter ->
                acc + if (bitCounter.bit0 > bitCounter.bit1) '0' else '1'
            }
        }

        fun oxygenRating(): String {
            var oxygenRating = readouts
            var index = 0

            while(oxygenRating.size > 1) {
                val bitCounter = count(oxygenRating, index)
                val bit = if (bitCounter.bit1 >= bitCounter.bit0) '1' else '0'
                oxygenRating = filter(oxygenRating, index, bit)
                index++
            }

            return oxygenRating[0]
        }

        fun co2Rating(): String {
            var co2Rating = readouts
            var index = 0

            while(co2Rating.size > 1) {
                val bitCounter = count(co2Rating, index)
                val bit = if (bitCounter.bit0 <= bitCounter.bit1) '0' else '1'
                co2Rating = filter(co2Rating, index, bit)
                index++
            }

            return co2Rating[0]
        }

        private fun count(diagnostic: List<String>, index: Int): BitCounter {
            val bitCounter = BitCounter()

            diagnostic.forEach {
                if (it[index] == '0') bitCounter.bit0++ else bitCounter.bit1++
            }

            return bitCounter
        }

        private fun filter(diagnostic: List<String>, index: Int, bit: Char): List<String> {
            return diagnostic.filter {
                it[index] == bit
            }
        }

    }

    data class BitCounter(
        var bit0: Int = 0,
        var bit1: Int = 0
    )

    fun String.inverseBits(): String =
        this.fold("") { acc, bit ->
            acc + if (bit == '0') '1' else '0'
        }

    fun String.binaryToDecimal(): Int {
        var result = 0.0

        this.reversed().forEachIndexed { index, bit ->
            val power = 2.0.pow(index.toDouble())
            result += bit.digitToInt() * power
        }

        return result.toInt()
    }

}