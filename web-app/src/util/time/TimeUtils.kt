package util.time

import moment.moment
import kotlin.js.Date

/**
 * @author sokomishalov
 */

fun timeAgo(date: Date): String {
    return moment(date).fromNow()
}