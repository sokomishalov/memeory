package ru.sokomishalov.memeory.service

import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.sokomishalov.memeory.dto.SourceDTO
import ru.sokomishalov.memeory.util.ObjectMapperHelper
import javax.annotation.PostConstruct


/**
 * @author sokomishalov
 */
@Service
class SourcesFromClasspathService(
        private val service: SourceService,
        @Value("classpath:sources.yml") private val resource: Resource
) {

    @PostConstruct
    fun init() {
        val sourcesTypeRef: TypeReference<List<SourceDTO>> = object : TypeReference<List<SourceDTO>>() {}
        val sourcesFromYaml: List<SourceDTO> = ObjectMapperHelper.yamlObjectMapper.readValue(resource.inputStream, sourcesTypeRef)

        service.saveBatchIfNotExist(sourcesFromYaml).subscribe()
    }
}
