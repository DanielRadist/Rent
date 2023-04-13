package ru.rent.app.model

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.rent.app.manufacturer.ManufacturerService
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.model.ModelJpaRepository
import ru.rent.domain.model.Model
import ru.rent.web.model.ModelDto
import ru.rent.web.model.ModelMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ModelService(
    private val modelJpaRepository: ModelJpaRepository,
    private val manufacturerService: ManufacturerService
) {
    fun save(modelDto: ModelDto): Model =
        modelDto.id?.let {
            modelJpaRepository.findByIdOrNull(it)?.let { model ->
                saveModel(
                    model.apply {
                        modelDto.name?.let { name -> this.name = name }

                        modelDto.manufacturer?.id?.let {
                            this.manufacturer = manufacturerService.findById(it) as ManufacturerEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД изготовителя")
                    }
                )
            }
        } ?: saveModel(modelDto.toEntity())

    fun saveModel(model: Model): Model =
        modelJpaRepository.save(model as ModelEntity)

    fun deleteById(modelId: UUID) =
        modelJpaRepository.deleteById(modelId)

    fun findAll(): List<Model> =
        modelJpaRepository.findAll()

    fun findById(modelId: UUID): Model =
        modelJpaRepository.findByIdOrNull(modelId)
            ?: throw EntityNotFoundException("Модель с УИД $modelId не найдена")
}