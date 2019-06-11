package ru.sokomishalov.memeory.config.hotfix

import com.fasterxml.classmate.ResolvedType
import com.fasterxml.classmate.TypeResolver
import com.fasterxml.classmate.types.ResolvedArrayType
import com.fasterxml.classmate.types.ResolvedObjectType
import com.fasterxml.classmate.types.ResolvedPrimitiveType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.plugin.core.PluginRegistry
import org.springframework.stereotype.Component
import springfox.documentation.schema.Collections.containerType
import springfox.documentation.schema.Collections.isContainerType
import springfox.documentation.schema.DefaultTypeNameProvider
import springfox.documentation.schema.Maps.isMapType
import springfox.documentation.schema.TypeNameExtractor
import springfox.documentation.schema.Types.typeNameFor
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.schema.EnumTypeDeterminer
import springfox.documentation.spi.schema.TypeNameProviderPlugin
import springfox.documentation.spi.schema.contexts.ModelContext
import java.lang.reflect.Type
import java.util.*
import java.util.Optional.ofNullable

/**
 * @author sokomishalov
 */

@Component
@Primary
class TypeNameExtractorAdapter(private val typeResolver: TypeResolver,
                               @param:Qualifier("typeNameProviderPluginRegistry") private val typeNameProviders: PluginRegistry<TypeNameProviderPlugin, DocumentationType>,
                               private val enumTypeDeterminer: EnumTypeDeterminer) : TypeNameExtractor(typeResolver, typeNameProviders, enumTypeDeterminer) {

    override fun typeName(context: ModelContext): String {
        return typeName(context, HashMap())
    }

    override fun typeName(
            context: ModelContext,
            knownNames: Map<String, String>): String {
        val type = asResolved(context.type)
        if (isContainerType(type)) {
            return containerType(type)
        }
        return if (knownNames.containsKey(context.typeId)) {
            knownNames[context.typeId].toString()
        } else innerTypeName(
                type,
                context,
                knownNames
        )
    }

    private fun asResolved(type: Type): ResolvedType {
        return typeResolver.resolve(type)
    }

    private fun genericTypeName(
            resolvedType: ResolvedType,
            context: ModelContext,
            knownNames: Map<String, String>): String {
        val erasedType = resolvedType.erasedType
        val namingStrategy = context.genericNamingStrategy
        val typeId = ModelContext.fromParent(
                context,
                resolvedType).typeId
        if (knownNames.containsKey(typeId)) {
            return knownNames[typeId].toString()
        }
        val simpleName = ofNullable(
                if (isContainerType(resolvedType)) containerType(resolvedType) else typeNameFor(erasedType))
                .orElse(modelName(
                        ModelContext.fromParent(
                                context,
                                resolvedType),
                        knownNames))
        val sb = StringBuilder(String.format(
                "%s%s",
                simpleName,
                namingStrategy.openGeneric))
        var first = true
        for (index in 0 until erasedType.typeParameters.size) {
            val typeParam = resolvedType.typeParameters[index]
            if (first) {
                sb.append(innerTypeName(
                        typeParam,
                        context,
                        knownNames))
                first = false
            } else {
                sb.append(String.format(
                        "%s%s",
                        namingStrategy.typeListDelimiter,
                        innerTypeName(
                                typeParam,
                                context,
                                knownNames)))
            }
        }
        sb.append(namingStrategy.closeGeneric)
        return sb.toString()
    }

    private fun innerTypeName(
            type: ResolvedType,
            context: ModelContext,
            knownNames: Map<String, String>): String {
        return if (type.typeParameters.size > 0 && type.erasedType.typeParameters.isNotEmpty()) {
            genericTypeName(
                    type,
                    context,
                    knownNames)
        } else simpleTypeName(
                type,
                context,
                knownNames)
    }

    private fun simpleTypeName(
            type: ResolvedType,
            context: ModelContext,
            knownNames: Map<String, String>): String {
        val erasedType = type.erasedType
        if (type is ResolvedPrimitiveType) {
            return typeNameFor(erasedType)
        } else if (enumTypeDeterminer.isEnum(erasedType)) {
            return "string"
        } else if (type is ResolvedArrayType) {
            val namingStrategy = context.genericNamingStrategy
            return String.format(
                    "Array%s%s%s",
                    namingStrategy.openGeneric,
                    simpleTypeName(
                            type.getArrayElementType(),
                            context,
                            knownNames),
                    namingStrategy.closeGeneric)
        } else if (type is ResolvedObjectType) {
            val typeName = typeNameFor(erasedType)
            if (typeName != null) {
                return typeName
            }
        }
        return modelName(
                ModelContext.fromParent(
                        context,
                        type),
                knownNames)
    }

    private fun modelName(
            context: ModelContext,
            knownNames: Map<String, String>): String {
        if (!isMapType(asResolved(context.type)) && knownNames.containsKey(context.typeId)) {
            return knownNames[context.typeId].toString()
        }
        val selected = typeNameProviders.getPluginOrDefaultFor(
                context.documentationType,
                DefaultTypeNameProvider()
        )
        return selected.nameFor((context.type as ResolvedType).erasedType)
    }
}
