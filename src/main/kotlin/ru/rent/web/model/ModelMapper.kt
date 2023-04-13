package ru.rent.web.model

import org.hibernate.Hibernate
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.domain.model.Model
import ru.rent.web.manufacturer.ManufacturerMapper.toDto
import ru.rent.web.manufacturer.ManufacturerMapper.toEntity
import java.util.*

object ModelMapper {
    fun Model.toDto(): ModelDto =
        ModelDto(
            id = this.id,
            name = this.name,
            manufacturer = this.manufacturer.takeIf { Hibernate.isInitialized(it) } ?.toDto()
        )

    fun List<Model>.toDto(): List<ModelDto> =
        this.map { it.toDto() }

    fun ModelDto.toEntity(): ModelEntity =
        ModelEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать название модели"
            ),
            manufacturer = this.manufacturer?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать одно из изготовителей"
                )
        )
}