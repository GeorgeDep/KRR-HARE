package hare.core

import hare.core.testDoubles.AppExecutorMock
import hare.testDoubles.PrintStreamSpy
import hare.testDoubles.ScannerMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class AppDelegateTests {
    // MARK: - Private properties

    private lateinit var sut: AppDelegate
    private val outputStream = PrintStreamSpy()
    private val inputStream = ScannerMock()
    private val executorFactory = AppExecutorMock()
    private val optionsMessages = listOf("Select execution mode", "a) Data Processing", "b) Charting", "c) Trading")

    // MARK: - Internal methods

    @BeforeTest
    fun setUp() {
        sut = AppDelegate(
            outputStream,
            readLine = { inputStream.value },
            appExecutorFactory = { executorFactory.build(it) }
        )
    }

    @Test
    fun start_CorrectOutputDisplayed_ValidSelection() {
        inputStream.value = "a"

        sut.start()

        assertEquals( listOf("===HARE===") + optionsMessages, outputStream.printedText.toList())
    }

    @Test
    fun start_CorrectOutputDisplayed_InvalidSelection() {
        var firstReadFailed = false
        sut = AppDelegate(outputStream, readLine = {
            val returnValue = if (firstReadFailed) "a" else "z"
            firstReadFailed = true
            return@AppDelegate returnValue
        })

        sut.start()

        assertEquals(
            listOf("===HARE===") + optionsMessages + "Invalid selection" + optionsMessages,
            outputStream.printedText.toList()
        )
    }

    @Test
    fun start_CorrectExecuterLaunched() {
        val (input, executor) = listOf(
            "a" to AppMode.Processing(),
            "b" to AppMode.Charting(),
            "c" to AppMode.Trading()
        ).random()
        inputStream.value = input

        sut.start()

        assertEquals(1, executorFactory.requests.size)
        assertEquals(executorFactory.requests.get(0)!!::class, executor::class)
    }
}