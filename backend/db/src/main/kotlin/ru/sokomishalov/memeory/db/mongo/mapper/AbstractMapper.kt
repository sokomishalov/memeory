@file:Suppress("unused")

package ru.sokomishalov.memeory.db.mongo.mapper


interface AbstractMapper<ENTITY, DTO> {
    fun toEntity(dto: DTO): ENTITY

    fun toDto(entity: ENTITY): DTO

    fun toEntityList(dtoList: Iterable<DTO>): List<ENTITY> = dtoList.map { toEntity(it) }

    fun toDtoList(entities: Iterable<ENTITY>): List<DTO> = entities.map { toDto(it) }
}