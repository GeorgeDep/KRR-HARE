package hare.processing.indicators

import hare.processing.Datapoint
import hare.utils.Queue
import kotlin.math.abs

class RelativeStrengthIndexIndicator(
    private val periods: Int = 14
): Indicator {
    // MARK: - Window

    private class Window(val gains: WindowPoint, val losses: WindowPoint, var lastDatapoint: Datapoint) {
        fun rsi(periods: Int): Float {
            if (losses.value == 0f && gains.value == 0f) { return 50f }
            if (losses.value == 0f) { return 100f }
            val averageGain = gains.value / periods
            val averageLoss = losses.value / periods
            return 100f - (100f / (1 + averageGain/averageLoss))
        }

        fun add(value: Float, datapoint: Datapoint) {
            arrayOf(gains, losses).forEach { it.dequeue() }
            losses.enqueue(if (value <= 0) abs(value) else 0f)
            gains.enqueue(if (value >= 0) abs(value) else 0f)
            lastDatapoint = datapoint
        }
    }

    // MARK: - WindowPoint

    private class WindowPoint(val queue: Queue<Float> = Queue(), var value: Float = 0f) {
        fun enqueue(value: Float) {
            queue.enqueue(value)
            this.value += value
        }

        fun dequeue() {
            val value = queue.dequeue()
            this.value -= value ?: 0f
        }
    }

    // MARK: - Overridden properties

    override val identifer: String = "RelativeStrengthIndex"

    // MARK: - Overridden methods

    override fun compute(datapoints: List<Datapoint>): List<Float> {
        if (datapoints.size < periods) { return List(datapoints.size) { 50f } }

        val result = MutableList(periods) { 50f}
        val window = buildWindow(datapoints.take(periods))
        datapoints.drop(periods).forEach {
            result.add(window.rsi(periods))
            window.add(diff(it, window.lastDatapoint), it)
        }

        return  result
    }

    // MARK: - Private methods

    private fun buildWindow(datapoints: List<Datapoint>): Window {
        val gains = WindowPoint()
        val losses = WindowPoint()

        var lastDatapoint = datapoints.first()
        for (datapoint in datapoints.drop(1)) {
            val diff = diff(datapoint, lastDatapoint)
            losses.enqueue(if (diff <= 0) abs(diff) else 0f)
            gains.enqueue(if (diff >= 0) abs(diff) else 0f)
            lastDatapoint = datapoint
        }

        return Window(gains, losses, lastDatapoint)
    }

    private fun diff(d1: Datapoint, d2: Datapoint): Float {
        return (d1.close - d2.close) / d2.close
    }
}