package ru.sokomishalov.memeory.service.provider.reddit

import ru.sokomishalov.memeory.autoconfigure.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class RedditCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "provider.reddit.enabled"
}
