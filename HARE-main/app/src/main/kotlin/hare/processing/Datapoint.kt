package hare.processing

import java.util.*

data class Datapoint(
    var date: Date,
    var open: Float,
    var high: Float,
    var low: Float,
    var close: Float,
    var volume: Double
) {
    val price: Float
        get() = (high - low) / 2
}
