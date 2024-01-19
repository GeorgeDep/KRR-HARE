package hare.processing.indicators

import hare.processing.Datapoint

class MovingAverageConvergenceDivergenceIndicator: Indicator {
    // MARK: - Overridden properties

    override val identifer: String = "MovingAverageConvergenceDivergence"

    // MARK: - Private properties

    private val ema12 = ExponentialMovingAverageIndicator(period = 12)
    private val ema26 = ExponentialMovingAverageIndicator(period = 26)

    // MARK: - Overridden methods

    override fun compute(datapoints: List<Datapoint>): List<Float> {
        val ema12 = ema12.compute(datapoints)
        val ema26 = ema26.compute(datapoints)
        return datapoints.indices.map { ema12[it] - ema26[it] }
    }
}