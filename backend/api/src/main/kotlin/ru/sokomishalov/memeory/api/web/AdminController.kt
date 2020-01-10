package ru.sokomishalov.memeory.api.web

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.web.bind.annotation.*
import ru.sokomishalov.memeory.api.scheduler.MemesFetchingScheduler
import ru.sokomishalov.memeory.core.dto.ChannelDTO
import ru.sokomishalov.memeory.core.dto.MemesPageRequestDTO
import ru.sokomishalov.memeory.db.ChannelService
import ru.sokomishalov.memeory.db.MemeService
import ru.sokomishalov.memeory.telegram.bot.MemeoryBot

/**
 * @author sokomishalov
 */
@RestController
@RequestMapping("/admin")
class AdminController(
        private val channelService: ChannelService,
        private val memeService: MemeService,
        private val bot: MemeoryBot,
        private val scheduler: MemesFetchingScheduler
) {

    @PostMapping("/channels/add")
    suspend fun add(@RequestBody channel: ChannelDTO): ChannelDTO? {
        return channelService.save(channel).firstOrNull()
    }

    @GetMapping("/memes/force-load")
    suspend fun forceLoad() {
        GlobalScope.launch {
            scheduler.loadMemes()
        }
    }

    @GetMapping("/bot/broadcast-batch/{page}/{count}")
    suspend fun broadcastBatch(
            @PathVariable("page") page: Int,
            @PathVariable("count") count: Int
    ) {
        GlobalScope.launch {
            val memes = memeService.getPage(MemesPageRequestDTO(
                    pageNumber = page,
                    pageSize = count
            ))
            bot.broadcastMemes(memes)
        }
    }

    @GetMapping("/bot/broadcast-batch/{id}")
    suspend fun broadcastOne(
            @PathVariable("id") id: String
    ) {
        GlobalScope.launch {
            val meme = memeService.findById(id)
            bot.broadcastMemes(listOfNotNull(meme))
        }
    }
}