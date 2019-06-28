package ru.sokomishalov.memeory.service.provider.instagram.scrape

import me.postaddict.instagram.scraper.Instagram
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.memeory.service.provider.instagram.InstagramCondition


/**
 * @author sokomishalov
 */
@Configuration
class InstagramScrapeConfig {

    @Bean
    @Conditional(InstagramCondition::class)
    fun okHttpClient(): OkHttpClient = OkHttpClient()

    @Bean
    @Conditional(InstagramCondition::class)
    fun instagram(okHttpClient: OkHttpClient): Instagram = Instagram(okHttpClient)

}
