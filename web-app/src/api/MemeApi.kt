package api

import dto.Meme
import util.http.request

/**
 * @author sokomishalov
 */

suspend fun loadMemes(
        pageNumber: Int = 0,
        pageSize: Int = 100
): Array<Meme> = request("/memes/page/$pageNumber/$pageSize")