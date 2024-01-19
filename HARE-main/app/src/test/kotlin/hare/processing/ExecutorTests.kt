package hare.processing

import hare.testDoubles.PrintStreamSpy
import hare.testDoubles.ScannerMock
import kotlin.test.BeforeTest
import kotlin.test.Test

class ExecutorTests {
    // MARK: - Private properties

    private lateinit var sut: Executor
    private val outputStream = PrintStreamSpy()
    private val inputStream = ScannerMock()

    // MARK: - Internal methods

    @BeforeTest
    fun setUp() {
        sut = Executor(outputStream, readLine = { inputStream.value })
    }

    @Test
    fun test() {
        val resourcePath = this::class.java.classLoader.getResource("sample.csv")!!.path
        inputStream.value = resourcePath

        sut.run()
    }
}