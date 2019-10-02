package api

import dto.Meme
import util.http.request

/**
 * @author sokomishalov
 */

suspend fun loadMemes(
        pageNumber: Int = 0,
        pageSize: Int = 100
): List<Meme> = request<Array<Meme>>("/memes/page/$pageNumber/$pageSize").toList()