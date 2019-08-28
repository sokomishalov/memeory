package ru.sokomishalov.memeory.util

import org.junit.Assert.*
import org.junit.Test
import ru.sokomishalov.memeory.AbstractTest
import ru.sokomishalov.memeory.util.io.checkAttachmentAvailability
import ru.sokomishalov.memeory.util.io.getImageAspectRatio
import ru.sokomishalov.memeory.util.io.getImageDimensions
import kotlin.math.abs

class ImageUtilsTest : AbstractTest() {

    private val imageWidth = 200
    private val imageHeight = 300
    private val imageUrl = "https://picsum.photos/$imageWidth/$imageHeight"
    private val invalidImageUrl = "https://lol.kek/cheburek"
    private val imageUrl401 = "https://httpstat.us/401"
    private val imageUrl404 = "https://httpstat.us/404"


    @Test
    fun `Get random image by url and check dimensions`() {
        val imageDimensions = getImageDimensions(imageUrl)

        assertEquals(imageWidth, imageDimensions.t1)
        assertEquals(imageHeight, imageDimensions.t2)
    }

    @Test
    fun `Get random image by url and check aspect ratio`() {
        val expected = imageWidth.toDouble().div(imageHeight)
        val result = getImageAspectRatio(imageUrl)

        assertTrue(abs(expected - result) < 0.01)
    }

    @Test
    fun `Check image availability`() {
        assertTrue(checkAttachmentAvailability(imageUrl))
        assertFalse(checkAttachmentAvailability(invalidImageUrl))
        assertFalse(checkAttachmentAvailability(imageUrl401))
        assertFalse(checkAttachmentAvailability(imageUrl404))
    }
}
