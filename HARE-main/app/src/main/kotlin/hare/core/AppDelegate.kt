package hare.core

import java.io.PrintStream
import java.util.*

class AppDelegate(
    private val outputStream: PrintStream = System.out,
    private val readLine: () -> String = { Scanner(System.`in`).next() },
    private val appExecutorFactory: (AppMode) -> AppExecutor? = { it.executor }
) {
    // MARK: - Private properties

    private val appModes = arrayOf(
        AppMode.Processing(),
        AppMode.Charting(),
        AppMode.Trading()
    )
    private val appModeIDMap: Map<String, AppMode> by lazy { appModes.associateBy({it.id}, {it}) }

    // MARK: - Public methods

    fun start(introMessage: String = "===HARE===") {
        outputStream.println(introMessage)
        showAvailableModes()
        val appMode = getAppMode()
        appMode ?: return start(introMessage = "Invalid selection")
        appExecutorFactory(appMode)?.run()
    }

    // MARK: - Private methods

    private fun showAvailableModes() {
        outputStream.println()
        outputStream.println("Select execution mode")
        appModes.forEach { outputStream.println("${it.id}) ${it.description}") }
        outputStream.println()
    }

    private fun getAppMode(): AppMode? {
        val input = readLine()
        return appModeIDMap[input]
    }
}