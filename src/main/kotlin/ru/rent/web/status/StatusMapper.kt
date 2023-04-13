package ru.rent.web.status

import ru.rent.data.status.StatusEntity
import ru.rent.domain.status.Status
import java.util.*

object StatusMapper {
    fun Status.toDto(): StatusDto =
        StatusDto(
            id = this.id,
            name = this.name
        )

    fun List<Status>.toDto(): List<StatusDto> =
        this.map { it.toDto() }

    fun StatusDto.toEntity(): StatusEntity =
        StatusEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать название товара"
            )
        )
}