package ru.sokomishalov.memeory.providers.util.html

import org.jsoup.Jsoup.clean
import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.safety.Whitelist
import ru.sokomishalov.commons.core.http.REACTIVE_NETTY_HTTP_CLIENT
import ru.sokomishalov.commons.core.reactor.awaitStrict
import java.nio.charset.StandardCharsets.UTF_8

/**
 * @author sokomishalov
 */

suspend fun getWebPage(url: String): Document {
    return REACTIVE_NETTY_HTTP_CLIENT
            .get()
            .uri(url)
            .responseContent()
            .aggregate()
            .asString(UTF_8)
            .awaitStrict()
            .let { parse(it) }
}

fun Element.removeLinks(): String? {
    val titleDoc = parse(html())

    val allAnchors = titleDoc.select("a")
    val hrefAnchors = titleDoc.select("a[href^=/]")
    val unwantedAnchors = mutableListOf<Element>()

    allAnchors.filterNotTo(unwantedAnchors) { hrefAnchors.contains(it) }
    unwantedAnchors.forEach { it.remove() }

    return titleDoc.text()
}

fun Element.getSingleElementByClass(name: String): Element {
    return getElementsByClass(name).first()
}

fun Element.getSingleElementByTag(name: String): Element {
    return getElementsByTag(name).first()
}

fun Element.getImageBackgroundUrl(): String {
    val style = attr("style")
    return style.substring(style.indexOf("http"), style.indexOf(")"))
}

fun Element.fixText(): String {
    return clean(toString(), Whitelist.simpleText())
}
