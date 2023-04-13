package ru.rent.web.rent

import ru.rent.data.rent.RentEntity
import ru.rent.domain.rent.Rent
import java.util.*

object RentMapper {
    fun Rent.toDto(): RentDto =
        RentDto(
                id = this.id,
                name = this.name
        )

    fun List<Rent>.toDto(): List<RentDto> =
        this.map { it.toDto() }

    fun RentDto.toEntity(): RentEntity =
        RentEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать номер договора"
            )
        )
}