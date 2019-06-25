package ru.sokomishalov.memeory.service.api.reddit

import ru.sokomishalov.memeory.condition.BaseBooleanPropertyCondition


/**
 * @author sokomishalov
 */
class RedditCondition : BaseBooleanPropertyCondition() {
    override val propertyName: String = "channel.reddit.enabled"
}
