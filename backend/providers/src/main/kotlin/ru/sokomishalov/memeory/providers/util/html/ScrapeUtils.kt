package ru.sokomishalov.memeory.providers.util.html

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Element

/**
 * @author sokomishalov
 */

fun Element.removeLinks(): String? {
    val titleDoc = parse(html())

    val allAnchors = titleDoc.select("a")
    val hrefAnchors = titleDoc.select("a[href^=/]")
    val unwantedAnchors = mutableListOf<Element>()

    allAnchors.filterNotTo(unwantedAnchors) { hrefAnchors.contains(it) }
    unwantedAnchors.forEach { it.remove() }

    return titleDoc.text()
}