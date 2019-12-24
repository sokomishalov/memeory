package ru.sokomishalov.memeory.providers.util.html

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.web.reactive.function.client.awaitBody
import ru.sokomishalov.commons.core.reactor.awaitStrict
import ru.sokomishalov.memeory.providers.util.client.CUSTOM_WEB_CLIENT

/**
 * @author sokomishalov
 */

internal suspend fun getWebPage(url: String): Document {
    return CUSTOM_WEB_CLIENT
            .get()
            .uri(url)
            .exchange()
            .awaitStrict()
            .awaitBody<String>()
            .let { parse(it) }
}

internal fun Element.removeLinks(): String? {
    val titleDoc = parse(html())

    val allAnchors = titleDoc.select("a")
    val hrefAnchors = titleDoc.select("a[href^=/]")
    val unwantedAnchors = mutableListOf<Element>()

    allAnchors.filterNotTo(unwantedAnchors) { hrefAnchors.contains(it) }
    unwantedAnchors.forEach { it.remove() }

    return titleDoc.text()
}

internal fun Element.getSingleElementByClass(name: String): Element {
    return getElementsByClass(name).first()
}

internal fun Element.getSingleElementByTag(name: String): Element {
    return getElementsByTag(name).first()
}

internal fun Element.getImageBackgroundUrl(): String {
    val style = attr("style")
    return style.substring(style.indexOf("http"), style.indexOf(")"))
}
