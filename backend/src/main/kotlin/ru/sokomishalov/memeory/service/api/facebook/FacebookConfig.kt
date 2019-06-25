package ru.sokomishalov.memeory.service.api.facebook

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.social.facebook.connect.FacebookConnectionFactory


/**
 * @author sokomishalov
 */
@Configuration
class FacebookConfig {

    @Bean
    @Conditional(FacebookCondition::class)
    fun facebook(props: FacebookConfigurationProperties): Facebook {
        val facebookConnectionFactory = FacebookConnectionFactory(props.appId, props.secret)
        val token = facebookConnectionFactory.oAuthOperations.authenticateClient("user_posts").accessToken

        return FacebookTemplate(token, null, props.appId)
    }
}
