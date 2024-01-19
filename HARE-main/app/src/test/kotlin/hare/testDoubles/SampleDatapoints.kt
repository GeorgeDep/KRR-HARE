package hare.testDoubles

import hare.processing.Datapoint
import java.util.*

val List<Datapoint>.sample
    get() = listOf(
        Datapoint(
            date = Date(1646106180000),
            open = 43046.58f,
            high = 43046.58f,
            low = 43046.58f,
            close = 43046.58f,
            volume = 0.0
        ),
        Datapoint(
            date = Date(1646106060000),
            open = 43018.23f,
            high = 43046.59f,
            low = 43018.23f,
            close = 43046.58f,
            volume = 6154.673020989
        ),
        Datapoint(
            date = Date(1646106000000),
            open = 43022.24f,
            high = 43022.24f,
            low = 43016.03f,
            close = 43016.03f,
            volume = 397.0379569
        ),
        Datapoint(
            date = Date(1646105940000),
            open = 43035.16f,
            high = 43035.16f,
            low = 42999.44f,
            close = 42999.44f,
            volume = 35300.390268
        ),
        Datapoint(
            date = Date(1646105880000),
            open = 43077.82f,
            high = 43077.82f,
            low = 43049.46f,
            close = 43049.46f,
            volume = 956.1431434163999
        )
    )