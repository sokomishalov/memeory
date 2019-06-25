package ru.sokomishalov.memeory.service.api.instagram

import me.postaddict.instagram.scraper.Instagram
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration


/**
 * @author sokomishalov
 */
@Configuration
class InstagramConfig {

    @Bean
    @Conditional(InstagramCondition::class)
    fun okHttpClient(): OkHttpClient = OkHttpClient()

    @Bean
    @Conditional(InstagramCondition::class)
    fun instagram(okHttpClient: OkHttpClient): Instagram = Instagram(okHttpClient)

}
