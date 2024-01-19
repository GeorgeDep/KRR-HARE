package hare.processing.parsers

import hare.processing.Datapoint
import hare.testDoubles.sample
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class PrasoonDatasetParserTests {
    // MARK: - Private properties

    private val sut = PrasoonDatasetParser()

    // MARK: - Internal methods

    @Test
    fun parse_ValidPath_CorrectOutput() {
        val resourcePath = this::class.java.classLoader.getResource("sample.csv")!!.path

        val result = sut.parse(resourcePath)

        assertEquals(listOf<Datapoint>().sample, result)
    }

    @Test
    fun parse_InvalidPath_ErrorThrown() {
        assertThrows<Exception> { sut.parse("invalid-path") }
    }
}