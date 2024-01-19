package hare.processing.indicators

import hare.processing.Datapoint

interface Indicator {
    val identifer: String

    fun compute(datapoints: List<Datapoint>): List<Float>
}