package info.petrsabata.advent2021

import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {

    val crabs = InputHelper("day07.txt").readText().split(',').map { it.toInt() }.sorted()

    var constantFuelConsumption = Int.MAX_VALUE
    var increasingFuelConsumption = Int.MAX_VALUE

    for (position in crabs.first()..crabs.last()) {
        constantFuelConsumption = min(constantFuelConsumption, crabs.sumOf { (it - position).absoluteValue })
        increasingFuelConsumption =
            min(
                increasingFuelConsumption,
                crabs.sumOf {
                    val moves = (it - position).absoluteValue
                    (moves * (1 + moves)) / 2
                }
            )
    }

    println("Constant fuel consumption: $constantFuelConsumption")
    println("Increasing fuel consumption: $increasingFuelConsumption")

}