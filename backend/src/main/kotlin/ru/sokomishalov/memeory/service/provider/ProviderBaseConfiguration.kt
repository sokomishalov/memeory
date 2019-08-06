package ru.sokomishalov.memeory.service.provider

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.memeory.service.provider.facebook.FacebookConfigurationProperties
import ru.sokomishalov.memeory.service.provider.instagram.InstagramConfigurationProperties
import ru.sokomishalov.memeory.service.provider.ninegag.NinegagConfigurationProperties
import ru.sokomishalov.memeory.service.provider.pinterest.PinterestConfigurationProperties
import ru.sokomishalov.memeory.service.provider.reddit.RedditConfigurationProperties
import ru.sokomishalov.memeory.service.provider.twitter.TwitterConfigurationProperties
import ru.sokomishalov.memeory.service.provider.vk.VkConfigurationProperties


/**
 * @author sokomishalov
 */
@Configuration
@EnableConfigurationProperties(
        FacebookConfigurationProperties::class,
        InstagramConfigurationProperties::class,
        RedditConfigurationProperties::class,
        TwitterConfigurationProperties::class,
        VkConfigurationProperties::class,
        NinegagConfigurationProperties::class,
        PinterestConfigurationProperties::class
)
class ProviderBaseConfiguration
