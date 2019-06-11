package ru.sokomishalov.memeory.config.hotfix

import com.fasterxml.classmate.ResolvedType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Component
import springfox.documentation.schema.Model
import springfox.documentation.schema.ModelProperty
import springfox.documentation.schema.plugins.SchemaPluginsManager
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.schema.ModelBuilderPlugin
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin
import springfox.documentation.spi.schema.SyntheticModelProviderPlugin
import springfox.documentation.spi.schema.ViewProviderPlugin
import springfox.documentation.spi.schema.contexts.ModelContext
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

/**
 * @author sokomishalov
 */
@Component
@Primary
class SchemaPluginsManagerAdapter @Autowired
constructor(
        @Qualifier("modelPropertyBuilderPluginRegistry") propertyEnrichers: PluginRegistry<ModelPropertyBuilderPlugin, DocumentationType>,
        @Qualifier("modelBuilderPluginRegistry") modelEnrichers: PluginRegistry<ModelBuilderPlugin, DocumentationType>,
        @param:Qualifier("viewProviderPluginRegistry") private val viewProviders: PluginRegistry<ViewProviderPlugin, DocumentationType>,
        @param:Qualifier("syntheticModelProviderPluginRegistry") private val syntheticModelProviders: PluginRegistry<SyntheticModelProviderPlugin, ModelContext>
) : SchemaPluginsManager(propertyEnrichers, modelEnrichers, viewProviders, syntheticModelProviders) {

    override fun viewProvider(documentationType: DocumentationType): ViewProviderPlugin {
        return viewProviders.getPluginFor(documentationType).orElseThrow<UnsupportedOperationException> { UnsupportedOperationException() }
    }

    override fun syntheticModel(context: ModelContext): Optional<Model> {
        return if (syntheticModelProviders.hasPluginFor(context)) {
            of(syntheticModelProviders.getPluginFor(context).orElseThrow<UnsupportedOperationException> { UnsupportedOperationException() }.create(context))
        } else empty()
    }

    override fun syntheticProperties(context: ModelContext): List<ModelProperty> {
        return if (syntheticModelProviders.hasPluginFor(context)) {
            syntheticModelProviders.getPluginFor(context).orElseThrow<UnsupportedOperationException> { UnsupportedOperationException() }.properties(context)
        } else ArrayList()
    }

    override fun dependencies(context: ModelContext): Set<ResolvedType> {
        return if (syntheticModelProviders.hasPluginFor(context)) {
            syntheticModelProviders.getPluginFor(context).orElseThrow<UnsupportedOperationException> { UnsupportedOperationException() }.dependencies(context)
        } else HashSet()
    }
}
