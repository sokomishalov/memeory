package ru.sokomishalov.memeory.service.api.reddit

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.reactive.function.client.WebClient


/**
 * @author sokomishalov
 */
@Configuration
class RedditConfig {

    @Bean
    @Qualifier("reddit-web-client")
    fun redditReactiveWebClient(props: RedditConfigurationProperties): WebClient {
        return WebClient
                .builder()
                .baseUrl(props.baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build()
    }
}
