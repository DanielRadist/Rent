package ru.rent.web.unit

import org.hibernate.Hibernate
import ru.rent.data.unit.UnitEntity
import ru.rent.domain.unit.Unit
import ru.rent.web.model.ModelMapper.toDto
import ru.rent.web.model.ModelMapper.toEntity
import ru.rent.web.status.StatusMapper.toDto
import ru.rent.web.status.StatusMapper.toEntity
import java.util.*

object UnitMapper {
    fun Unit.toDto(): UnitDto =
        UnitDto(
                id = this.id,
                status = this.status.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
                model = this.model.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
                price = this.price
        )

    fun List<Unit>.toDto(): List<UnitDto> =
        this.map { it.toDto() }

    fun UnitDto.toEntity(): UnitEntity =
        UnitEntity(
                id = this.id ?: UUID.randomUUID(),
                status = this.status?.toEntity()
                    ?: throw UninitializedPropertyAccessException(
                        message = "Необходимо указать один из товаров"
                    ),
                model = this.model?.toEntity()
                    ?: throw UninitializedPropertyAccessException(
                        message = "Необходимо указать одну из моделей изготовителя"
                    ),
                price = this.price ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать цену товара"
                )
        )
}