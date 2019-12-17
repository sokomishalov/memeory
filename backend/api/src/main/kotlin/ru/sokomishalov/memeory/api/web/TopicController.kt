package ru.sokomishalov.memeory.api.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sokomishalov.memeory.core.dto.TopicDTO
import ru.sokomishalov.memeory.db.TopicService

/**
 * @author sokomishalov
 */

@RestController
@RequestMapping("/topics")
class TopicController(
        private val topicService: TopicService
) {

    @GetMapping("/list")
    suspend fun list(): List<TopicDTO> {
        return topicService.findAll()
    }
}
