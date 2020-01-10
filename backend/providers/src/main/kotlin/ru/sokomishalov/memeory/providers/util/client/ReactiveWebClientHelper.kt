package ru.sokomishalov.memeory.providers.util.client

import ru.sokomishalov.commons.spring.webclient.createReactiveWebClient

/**
 * @author sokomishalov
 */

@PublishedApi
internal val CUSTOM_WEB_CLIENT = createReactiveWebClient(
        maxBufferSize = 16 * 1024 * 1024
)