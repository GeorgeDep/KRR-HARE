package hare.testDoubles

import java.io.PrintStream

class PrintStreamSpy: PrintStream(System.out) {
    // MARK: - Public properties

    var printedText = mutableListOf<String?>()

    // MARK: - Overridden methods

    override fun println(x: String?) {
        printedText.add(x)
    }
}