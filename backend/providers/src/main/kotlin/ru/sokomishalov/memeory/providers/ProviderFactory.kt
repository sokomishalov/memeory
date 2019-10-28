package ru.sokomishalov.memeory.providers

import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.enums.Provider

/**
 * @author sokomishalov
 */
@Component
class ProviderFactory(private val providers: List<ProviderService>) {

    fun getService(provider: Provider?): ProviderService? {
        return providers.find { it.provider == provider }
    }

}