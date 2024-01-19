package hare.processing.indicators

import hare.processing.Datapoint

class ExponentialMovingAverageIndicator(
    private val period: Int
): Indicator {
    // MARK: - Overridden properties

    override val identifer: String = "ExponentialMovingAverage"

    // MARK: - Overridden methods

    override fun compute(datapoints: List<Datapoint>): List<Float> {
        if (datapoints.size < period) { return emptyList() }

        val movingAverage = movingAverage(datapoints.take(period))
        val result = MutableList(period) { movingAverage }

        datapoints.drop(period).forEach {
            result.add((it.price - result.last) * 2 / (period+1) + result.last)
        }

        return result
    }

    // MARK: - Private methods

    private fun movingAverage(datapoints: List<Datapoint>): Float {
        val sum = datapoints.map { it.price }.sum()
        return sum / datapoints.size
    }
}