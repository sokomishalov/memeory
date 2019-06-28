package ru.sokomishalov.memeory.util

import reactor.util.function.Tuple2
import reactor.util.function.Tuples.of
import java.net.URL
import javax.imageio.ImageIO.read


/**
 * @author sokomishalov
 */

fun getImageAspectRatio(url: String): Double {
    return getImageDimensions(url)
            .let { t: Tuple2<Int, Int> -> t.t1.toDouble().div(t.t2) }
}

fun getImageDimensions(url: String): Tuple2<Int, Int> {
    return try {
        URL(url)
                .let { read(it) }
                .let { of(it.width, it.height) }
    } catch (t: Throwable) {
        of(1, 1)
    }
}
