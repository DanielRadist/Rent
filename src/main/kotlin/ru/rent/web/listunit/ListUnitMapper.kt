package ru.rent.web.listunit

import org.hibernate.Hibernate
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.domain.listunit.ListUnit
import ru.rent.web.rent.RentMapper.toDto
import ru.rent.web.rent.RentMapper.toEntity
import ru.rent.web.unit.UnitMapper.toDto
import ru.rent.web.unit.UnitMapper.toEntity
import java.util.*

object ListUnitMapper {
    fun ListUnit.toDto(): ListUnitDto =
        ListUnitDto(
            id = this.id,
            unit = this.unit.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
            rent = this.rent.takeIf { Hibernate.isInitialized(it) } ?.toDto()
        )

    fun List<ListUnit>.toDto(): List<ListUnitDto> =
        this.map { it.toDto() }

    fun ListUnitDto.toEntity(): ListUnitEntity =
        ListUnitEntity(
            id = this.id ?: UUID.randomUUID(),

            unit = this.unit?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать единицу оборудования"
                ),

            rent = this.rent?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать аренду"
                )
        )
}