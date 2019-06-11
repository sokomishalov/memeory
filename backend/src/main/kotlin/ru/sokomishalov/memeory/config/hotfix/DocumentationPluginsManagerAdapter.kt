package ru.sokomishalov.memeory.config.hotfix

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Component
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.DefaultsProviderPlugin
import springfox.documentation.spi.service.ResourceGroupingStrategy
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder
import springfox.documentation.spring.web.SpringGroupingStrategy
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager

@Component
@Primary
class DocumentationPluginsManagerAdapter(
        @param:Qualifier("defaultsProviderPluginRegistry") private val defaultsProviders: PluginRegistry<DefaultsProviderPlugin, DocumentationType>,
        @param:Qualifier("resourceGroupingStrategyRegistry") private val resourceGroupingStrategies: PluginRegistry<ResourceGroupingStrategy, DocumentationType>
) : DocumentationPluginsManager() {

    override fun resourceGroupingStrategy(documentationType: DocumentationType): ResourceGroupingStrategy {
        return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, SpringGroupingStrategy())
    }

    override fun createContextBuilder(documentationType: DocumentationType, defaultConfiguration: DefaultsProviderPlugin): DocumentationContextBuilder {
        return defaultsProviders
                .getPluginOrDefaultFor(documentationType, defaultConfiguration)
                .create(documentationType)
                .withResourceGroupingStrategy(resourceGroupingStrategy(documentationType))
    }
}

