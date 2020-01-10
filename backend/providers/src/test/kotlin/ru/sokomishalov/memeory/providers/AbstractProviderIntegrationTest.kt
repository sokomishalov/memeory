@file:Suppress("unused")

package ru.sokomishalov.memeory.providers

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.core.util.image.getImageByteArray


/**
 * @author sokomishalov
 */
abstract class AbstractProviderIntegrationTest {

    companion object : Loggable

    protected abstract val channel: ChannelDTO
    protected abstract val service: ProviderService

    @Test
    fun `Check that channel memes has been fetched`() {
        val memes = runBlocking { service.fetchMemes(channel) }
        logInfo("Memes: ${OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(memes)}")

        assertFalse(memes.isNullOrEmpty())
        memes.forEach {
            assertNotNull(it.id)
            assertNotNull(it.publishedAt)
            it.attachments.forEach { a ->
                assertTrue(a.type != NONE)
                assertFalse(a.url.isNullOrBlank())
            }
        }
    }

    @Test
    fun `Check that channel logo has been fetched`() {
        val image = runBlocking { getImageByteArray(service.getLogoUrl(channel)) } ?: ByteArray(0)

        assertNotEquals(0, image.size)
    }
}