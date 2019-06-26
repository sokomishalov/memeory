package ru.sokomishalov.memeory.service.api.twitter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.memeory.util.EMPTY
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.conf.Configuration as Twitter4jConfiguration


/**
 * @author sokomishalov
 */
@Configuration
class TwitterConfig {

    @Bean
    @Conditional(TwitterCondition::class)
    fun twitter(configuration: Twitter4jConfiguration): Twitter = TwitterFactory(configuration).instance

    @Bean
    @Conditional(TwitterCondition::class)
    fun configuration(): Twitter4jConfiguration {
        return ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(EMPTY)
                .setOAuthConsumerSecret(EMPTY)
                .setOAuthAccessToken(EMPTY)
                .setOAuthAccessTokenSecret(EMPTY)
                .build()
    }
}
