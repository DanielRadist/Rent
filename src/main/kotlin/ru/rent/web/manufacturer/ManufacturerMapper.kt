package ru.rent.web.manufacturer

import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.domain.manufacturer.Manufacturer
import java.util.*

object ManufacturerMapper {
    fun Manufacturer.toDto(): ManufacturerDto =
        ManufacturerDto(
            id = this.id,
            name = this.name,
            country = this.country,
            site = this.site
        )

    fun List<Manufacturer>.toDto(): List<ManufacturerDto> =
        this.map { it.toDto() }

    fun ManufacturerDto.toEntity(): ManufacturerEntity =
        ManufacturerEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать наименование производителя"
            ),
            country = this.country ?: "",
            site = this.site
        )
}