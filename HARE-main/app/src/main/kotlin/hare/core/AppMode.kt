package hare.core

import hare.charting.Executor as ChartingExecutor
import hare.processing.Executor as ProcessingExecutor
import hare.trading.Executor as TradinggExecutor

sealed class AppMode(
    internal val id: String,
    internal val description: String,
    val executor: AppExecutor
) {
    class Processing(): AppMode(id = "a", description = "Data Processing", ProcessingExecutor())
    class Charting(): AppMode(id = "b", description = "Charting", ChartingExecutor())
    class Trading(): AppMode(id = "c", description = "Trading", TradinggExecutor())
}