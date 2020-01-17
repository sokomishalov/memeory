package ru.sokomishalov.memeory.providers

import org.springframework.stereotype.Component
import ru.sokomishalov.memeory.core.enums.Provider
import ru.sokomishalov.memeory.core.enums.Provider.*
import ru.sokomishalov.skraper.Skraper
import ru.sokomishalov.skraper.SkraperClient
import ru.sokomishalov.skraper.client.reactornetty.ReactorNettySkraperClient
import ru.sokomishalov.skraper.provider.facebook.FacebookSkraper
import ru.sokomishalov.skraper.provider.ifunny.IFunnySkraper
import ru.sokomishalov.skraper.provider.instagram.InstagramSkraper
import ru.sokomishalov.skraper.provider.ninegag.NinegagSkraper
import ru.sokomishalov.skraper.provider.pinterest.PinterestSkraper
import ru.sokomishalov.skraper.provider.reddit.RedditSkraper
import ru.sokomishalov.skraper.provider.twitter.TwitterSkraper
import ru.sokomishalov.skraper.provider.vk.VkSkraper

/**
 * @author sokomishalov
 */
@Component
class ProviderFactory {

    private val client: SkraperClient = ReactorNettySkraperClient()

    private val providers: Map<Provider, Skraper> = mapOf(
            REDDIT to RedditSkraper(client = client),
            FACEBOOK to FacebookSkraper(client = client),
            INSTAGRAM to InstagramSkraper(client = client),
            TWITTER to TwitterSkraper(client = client),
            NINEGAG to NinegagSkraper(client = client),
            PINTEREST to PinterestSkraper(client = client),
            VK to VkSkraper(client = client),
            IFUNNY to IFunnySkraper(client = client)
    )

    operator fun get(provider: Provider?): Skraper {
        return providers[provider]!!
    }

}