package hare.processing

import hare.core.AppExecutor
import hare.processing.indicators.Indicator
import hare.processing.indicators.MovingAverageConvergenceDivergenceIndicator
import hare.processing.indicators.RelativeStrengthIndexIndicator
import hare.processing.parsers.DataParser
import hare.processing.parsers.PrasoonDatasetParser
import hare.utils.ThreadsafeProperty
import kotlinx.coroutines.*
import java.io.PrintStream
import java.util.*

class Executor(
    private val outputStream: PrintStream = System.out,
    private val readLine: () -> String = { Scanner(System.`in`).next() },
    private val parser: DataParser = PrasoonDatasetParser()
): AppExecutor {
    // MARK: - Private properties

    private val indicators: Array<Indicator> = arrayOf(
        RelativeStrengthIndexIndicator(),
        MovingAverageConvergenceDivergenceIndicator()
    )

    // MARK: - Overridden methods

    override fun run() {
        outputStream.println("Enter csv input path:")
        val datapoints = parse(filePath = readLine())
        runBlocking {
            aggregate(datapoints)
            outputStream.println("\n\uD83C\uDFC1 All tasks completed")
        }
    }

    // MARK: - Private methods

    private suspend fun aggregate(datapoints: List<Datapoint>) = coroutineScope {
        val aggregator = Aggregator()
        val timeframes = arrayOf(1,2,3,5,10,15,30,60,120,180,360)

        val generatedTimeframesCount = ThreadsafeProperty(initialValue = 0)
        val showProgress: suspend () -> Unit = suspend {
            delay(100)
            val status = if (generatedTimeframesCount.value < timeframes.size) "\uD83D\uDFE1 Generating" else "\uD83D\uDFE2 Generated"
            outputStream.print("\r$status timeframe data [${generatedTimeframesCount.value}/${timeframes.size}]")
        }

        val outputPath = System.getProperty("user.dir")
        for (timeframe in timeframes) {
            showProgress()
            launch(Dispatchers.Default) {
                val aggregatedPoints = aggregator.aggregate(datapoints, timeframe)
                val indicators = indicators.map { DataParser.Indicator(it.identifer, it.compute(aggregatedPoints)) }.toMutableList()
                val predictions = (0..<aggregatedPoints.size-1).map {
                    if (aggregatedPoints[it].price <= aggregatedPoints[it + 1].price) 1f else 0f
                } + listOf(1f)
                indicators.add(DataParser.Indicator("prediction", predictions))
                generatedTimeframesCount.set(generatedTimeframesCount.value + 1)
                parser.parse( aggregatedPoints, indicators, outputPath = "$outputPath/${timeframe}tf.csv")
                showProgress()
            }
        }
    }

    private fun parse(filePath: String): List<Datapoint> {
        outputStream.print("\uD83D\uDFE1 Parsing input file")
        val datapoints = parser.parse(filePath)
        outputStream.println("\r\uD83D\uDFE2 Input file parsed")
        return datapoints
    }
}