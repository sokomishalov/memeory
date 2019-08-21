package ru.sokomishalov.memeory.service.provider.vk.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes


/**
 * @author sokomishalov
 */

class VkApiBadFieldsExclusions : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

    override fun shouldSkipField(f: FieldAttributes?): Boolean = f!!.name == "anonymous"
}
