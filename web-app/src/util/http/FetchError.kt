package util.http

/**
 * @author sokomishalov
 */

class FetchError(
        message: String,
        status: Number,
        response: dynamic
) : Error(message)