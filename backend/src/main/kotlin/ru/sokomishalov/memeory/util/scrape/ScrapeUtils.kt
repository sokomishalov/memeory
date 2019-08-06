package ru.sokomishalov.memeory.util.scrape

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Document
import org.jsoup.Jsoup.connect as jsoupConnect


/**
 * @author sokomishalov
 */

suspend fun getWebPage(url: String): Document = withContext(IO) {
    jsoupConnect(url).get()
}
