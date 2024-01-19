package hare.processing.parsers

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import hare.processing.Datapoint

abstract class DataParser {
    // MARK: - Indicator

    data class Indicator(val identifier: String, val data: List<Float>)

    // MARK: - Private properties

    private val Datapoint.csvRow: List<String>
        get() = listOf(
            date.toInstant().epochSecond.toString(),
            open.toString(),
            high.toString(),
            low.toString(),
            close.toString(),
            volume.toString()
        )

    // MARK: - Public methods

    abstract fun parse(filePath: String): List<Datapoint>

    fun parse(datapoints: List<Datapoint>, indicators: List<Indicator>, outputPath: String) {
        csvWriter().open(outputPath) {
            writeRow(listOf("date", "open", "high", "low", "close", "volume") + indicators.map { it.identifier })
            datapoints.indices.forEach { index ->
                writeRow(datapoints[index].csvRow + indicators.map { it.data[index].toString() })
            }
        }
    }
}
