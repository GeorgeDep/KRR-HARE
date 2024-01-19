package hare.processing.indicators

import hare.processing.Datapoint
import java.util.Date
import kotlin.math.abs

class SimpleMovingAverageIndicator: Indicator {
    // MARK: - Overridden properties

    override val identifer: String = "SimpleMovingAverage"

    // MARK: - Private properties

    private val millisecondsIn24Hours: Long = 24 * 60 * 60 * 1000

    // MARK: - Overridden methods

    override fun compute(datapoints: List<Datapoint>): List<Float> {
        val result = mutableListOf<Float>()
        var windowDate = Date(0)
        var partialSum = 0f
        var intervalsCount = 0
        val appendToResult = fun() {
            if (intervalsCount == 0) { return }
            val movingAverage = partialSum / intervalsCount
            result.addAll(List(intervalsCount) { movingAverage })
        }

        for (datapoint in datapoints) {
            val isSameDay = abs(datapoint.date.time - windowDate.time) <= millisecondsIn24Hours
            if (isSameDay) {
                partialSum += datapoint.price
                intervalsCount += 1
            } else {
                appendToResult()
                windowDate = datapoint.date
                partialSum = datapoint.price
                intervalsCount = 1
            }
        }
        appendToResult()

        return result
    }
}