package ru.sokomishalov.memeory

import org.junit.jupiter.api.BeforeEach
import reactor.blockhound.integration.ReactorIntegration
import reactor.blockhound.BlockHound.install as blockHoundInstall


/**
 * @author sokomishalov
 */
open class AbstractMemeoryTest {

    @BeforeEach
    fun `Set up tests`() {
        blockHoundInstall(ReactorIntegration())
    }
}
