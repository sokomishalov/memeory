package ru.sokomishalov.memeory.service.provider.vk.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import ru.sokomishalov.memeory.service.provider.vk.VkCondition
import ru.sokomishalov.memeory.service.provider.vk.VkConfigurationProperties


/**
 * @author sokomishalov
 */
@Configuration
class VkApiConfig(
        private val props: VkConfigurationProperties
) {
    @Bean
    @Conditional(VkCondition::class, VkApiCondition::class)
    fun fixedGsonForVk(): Gson = GsonBuilder()
            .addDeserializationExclusionStrategy(VkApiBadFieldsExclusions())
            .create()

    @Bean
    @Conditional(VkCondition::class, VkApiCondition::class)
    fun apiClient(): VkApiClient = VkApiClient(HttpTransportClient(), fixedGsonForVk(), 3)

    @Bean
    @Conditional(VkCondition::class, VkApiCondition::class)
    fun serviceActor(vkApiClient: VkApiClient): ServiceActor = ServiceActor(props.appId, props.accessToken)
}
