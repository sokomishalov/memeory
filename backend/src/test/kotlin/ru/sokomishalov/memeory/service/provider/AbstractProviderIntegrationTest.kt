package ru.sokomishalov.memeory.service.provider

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.mock.mockito.MockBean
import ru.sokomishalov.commons.core.images.getImageByteArray
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.AbstractSpringTest
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.service.scheduler.MemesFetchingScheduler


/**
 * @author sokomishalov
 */
abstract class AbstractProviderIntegrationTest : AbstractSpringTest(), Loggable {

    protected abstract val channel: ChannelDTO

    @MockBean
    @Suppress("unused")
    lateinit var scheduler: MemesFetchingScheduler

    @Autowired
    lateinit var providers: List<ProviderService>

    @Autowired
    @Qualifier("placeholder")
    lateinit var placeholder: ByteArray

    @Test
    fun `Check that channel memes has been fetched`() {
        val service = providers.find { it.sourceType() == channel.sourceType }!!
        val memes = runBlocking { service.fetchMemesFromChannel(channel) }
        log("Memes: ${OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(memes)}")

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
        val service = providers.find { it.sourceType() == channel.sourceType }!!
        val image = runBlocking { getImageByteArray(service.getLogoUrlByChannel(channel), orElse = placeholder) }

        assertNotEquals(placeholder.size, image.size)
        assertNotEquals(placeholder, image)
    }
}