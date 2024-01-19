package hare.processing

import kotlin.math.max
import kotlin.math.min

internal class Aggregator {
    // MARK: - Internal methods

    fun aggregate(datapoints: List<Datapoint>, timeframe: Int): List<Datapoint> {
        if (datapoints.isEmpty() || timeframe <= 0) { return listOf() }

        val aggregatedData = mutableListOf(datapoints.first())
        var currentFrame = 1

        for (dataPoint in datapoints.drop(1)) {
            if (currentFrame >= timeframe) {
                aggregatedData.add(dataPoint)
                currentFrame = 1
                continue
            }

            aggregatedData.last().low = min(dataPoint.low, aggregatedData.last().low)
            aggregatedData.last().high = max(dataPoint.high, aggregatedData.last().high)
            aggregatedData.last().close = dataPoint.close
            aggregatedData.last().volume += dataPoint.volume
            currentFrame += 1
        }

        return aggregatedData
    }
}