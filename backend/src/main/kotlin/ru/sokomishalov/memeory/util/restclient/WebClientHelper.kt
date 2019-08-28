@file:Suppress("unused")

package ru.sokomishalov.memeory.util.restclient

import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import ru.sokomishalov.memeory.util.serialization.OBJECT_MAPPER
import org.springframework.web.reactive.function.client.ExchangeStrategies.builder as exchangeStrategiesBuilder
import org.springframework.web.reactive.function.client.WebClient.builder as webClientBuilder
import reactor.netty.http.client.HttpClient.create as httpClientCreate

/**
 * @author sokomishalov
 */

val REACTIVE_WEB_CLIENT = webClientBuilder()
        .exchangeStrategies(exchangeStrategiesBuilder()
                .codecs {
                    it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(OBJECT_MAPPER))
                    it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(OBJECT_MAPPER))
                }
                .build()
        )
        .clientConnector(ReactorClientHttpConnector(httpClientCreate().followRedirect(true)))
        .build()