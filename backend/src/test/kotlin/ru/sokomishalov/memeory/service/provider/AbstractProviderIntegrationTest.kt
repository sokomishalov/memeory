package ru.sokomishalov.memeory.service.provider

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.sokomishalov.memeory.AbstractSpringTest
import ru.sokomishalov.memeory.dto.ChannelDTO
import ru.sokomishalov.memeory.enums.AttachmentType.NONE
import ru.sokomishalov.memeory.util.extensions.await
import ru.sokomishalov.memeory.util.log.Loggable
import ru.sokomishalov.memeory.util.serialization.OBJECT_MAPPER


/**
 * @author sokomishalov
 */
abstract class AbstractProviderIntegrationTest : AbstractSpringTest(), Loggable {

    protected abstract val channel: ChannelDTO

    @Autowired
    lateinit var providers: List<ProviderService>

    @Test
    fun `Provider integration test`() {
        val service = providers.find { it.sourceType() == channel.sourceType }!!
        val memes = runBlocking { service.fetchMemesFromChannel(channel).await() }
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
}