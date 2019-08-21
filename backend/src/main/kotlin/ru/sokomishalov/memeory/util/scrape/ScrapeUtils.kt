package ru.sokomishalov.memeory.util.scrape

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.Jsoup.connect as jsoupConnect


/**
 * @author sokomishalov
 */

suspend fun getWebPage(url: String): Document = withContext(IO) {
    jsoupConnect(url).get()
}

fun Element.getSingleElementByClass(name: String): Element {
    return this.getElementsByClass(name).first()
}

fun Element.getImageBackgroundUrl(): String {
    return this
            .attr("style")
            .let { it.substring(it.indexOf("http"), it.indexOf(")")) }
}

fun Element.fixCaption(): String {
    val titleDoc = parse(this.html())

    val allAnchors = titleDoc.select("a")
    val twitterAnchors = titleDoc.select("a[href^=/]")
    val unwantedAnchors = ArrayList<Element>()

    allAnchors.filterNotTo(unwantedAnchors) { twitterAnchors.contains(it) }
    unwantedAnchors.forEach { it.remove() }

    return titleDoc.text()

}
