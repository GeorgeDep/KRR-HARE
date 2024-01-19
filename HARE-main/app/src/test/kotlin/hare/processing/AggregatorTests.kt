package hare.processing

import hare.testDoubles.sample
import java.util.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AggregatorTests {
    // MARK: - Private properties

    private val sut = Aggregator()

    // MARK: - Internal methods

    @Test
    fun aggregate_NoDataPoints_EmptyResult() {
        val result = sut.aggregate(listOf(), Random.nextInt(from = 1, until = 100))

        assertTrue(result.isEmpty())
    }

    @Test
    fun aggregate_InvalidTimeframe_EmptyResult() {
        val result = sut.aggregate(listOf<Datapoint>().sample, Random.nextInt(from = -100, until = 0))

        assertTrue(result.isEmpty())
    }

    @Test
    fun aggregate_UnitTimeframe_CorrectResult() {
        val result = sut.aggregate(listOf<Datapoint>().sample, timeframe = 1)

        assertEquals(listOf<Datapoint>().sample, result)
    }

    @Test
    fun aggregate_SmallerThanSampleTimeframe_CorrectResult() {
        val result = sut.aggregate(listOf<Datapoint>().sample, timeframe = 3)

        assertEquals(listOf(
            Datapoint(
                date = Date(1646106180000),
                open = 43046.58f,
                high = 43046.59f,
                low = 43016.03f,
                close = 43016.03f,
                volume = 6551.710977889
            ),
            Datapoint(
                date = Date(1646105940000),
                open = 43035.16f,
                high = 43077.82f,
                low = 42999.44f,
                close = 43049.46f,
                volume = 36256.5334114164
            )
        ), result)
    }

    @Test
    fun aggregate_EqualToSampleTimeframe_CorrectResult() {
        val result = sut.aggregate(listOf<Datapoint>().sample, timeframe = 5)

        assertEquals(listOf(
            Datapoint(
                date = Date(1646106180000),
                open = 43046.58f,
                high = 43077.82f,
                low = 42999.44f,
                close = 43049.46f,
                volume = 42808.2443893054
            )
        ), result)
    }

    @Test
    fun aggregate_GreaterThanSampleTimeframe_CorrectResult() {
        val result = sut.aggregate(listOf<Datapoint>().sample, timeframe = 8)

        assertEquals(listOf(
            Datapoint(
                date = Date(1646106180000),
                open = 43046.58f,
                high = 43077.82f,
                low = 42999.44f,
                close = 43049.46f,
                volume = 42808.2443893054
            )
        ), result)
    }
}