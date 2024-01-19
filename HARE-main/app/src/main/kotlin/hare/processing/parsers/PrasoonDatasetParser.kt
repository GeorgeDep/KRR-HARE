package hare.processing.parsers

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import hare.processing.Datapoint
import java.util.*

class PrasoonDatasetParser: DataParser() {
    // MARK: - CSVRow

    private enum class CSVRow(val value: Int) {
        UNIX(0), OPEN(3), HIGH(4), LOW(5), CLOSE(6), VOLUME(8)
    }

    // MARK: - Overridden methods

    override fun parse(filePath: String): List<Datapoint> {
        val datapoints = mutableListOf<Datapoint>()

        csvReader().open(filePath) {
            readAllAsSequence().forEach { row ->
                datapoints.add(Datapoint(
                    date = Date(row[CSVRow.UNIX.value].toLong() * 1000),
                    open = row[CSVRow.OPEN.value].toFloat(),
                    high = row[CSVRow.HIGH.value].toFloat(),
                    low = row[CSVRow.LOW.value].toFloat(),
                    close = row[CSVRow.CLOSE.value].toFloat(),
                    volume = row[CSVRow.VOLUME.value].toDouble()
                ))
            }
        }

        return datapoints
    }
}