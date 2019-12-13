@file:Suppress("unused")

package ru.sokomishalov.memeory.providers

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import ru.sokomishalov.commons.core.log.Loggable
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.core.util.image.getImageByteArray


/**
 * @author sokomishalov
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
abstract class AbstractProviderIntegrationTest : Loggable {

    protected abstract val channel: ChannelDTO

    private lateinit var service: ProviderService

    @Autowired
    lateinit var providerFactory: ProviderFactory

    @Before
    fun setUp() {
        service = providerFactory.getService(channel.provider)!!
    }

    @Test
    fun `Check that channel memes has been fetched`() {
        val memes = runBlocking { service.fetchMemes(channel) }
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
        val orElse = ByteArray(0)
        val image = runBlocking { getImageByteArray(service.getLogoUrl(channel), orElse) }

        assertNotEquals(orElse.size, image.size)
        assertNotEquals(orElse, image)
    }
}