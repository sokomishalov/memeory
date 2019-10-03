@file:Suppress("unused", "UNCHECKED_CAST")

package util.http

import kotlinx.coroutines.await
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import util.env.EnvHolder
import util.http.HttpMethod.GET
import kotlin.browser.window

suspend fun raiseError(res: Response): FetchError {
    val errorResponse: dynamic = runCatching { res.json().await() }.getOrElse { res.text().await() }
    return FetchError("Request failed", res.status, errorResponse)
}

suspend fun <T> request(
        uri: String = "/",
        baseURL: String = EnvHolder.backendUrl,
        url: String = "$baseURL$uri",
        method: HttpMethod = GET,
        body: dynamic = null
): T {
    val res = window.fetch(url, object : RequestInit {
        override var method: String? = method.name
        override var body: dynamic = body
    }).await()

    return when {
        res.ok -> res.json().await() as T
        else -> throw raiseError(res)
    }
}

fun buildLogoUrl(channelId: String): String {
    return "${EnvHolder.backendUrl}/channels/${channelId}"
}