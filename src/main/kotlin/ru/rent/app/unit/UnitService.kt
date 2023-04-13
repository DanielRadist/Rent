package ru.rent.app.unit

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.rent.app.manufacturer.ManufacturerService
import ru.rent.app.model.ModelService
import ru.rent.app.status.StatusService
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.unit.UnitEntity
import ru.rent.data.unit.UnitJpaRepository
import ru.rent.data.status.StatusEntity
import ru.rent.domain.unit.Unit
import ru.rent.web.unit.UnitDto
import ru.rent.web.unit.UnitMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class UnitService(
        private val unitJpaRepository: UnitJpaRepository,
        private val statusService: StatusService,
        private val modelService: ModelService,
        private val manufacturerService: ManufacturerService
) {
    fun save(unitDto: UnitDto): Unit =
        unitDto.id?.let {
            unitJpaRepository.findByIdOrNull(it)?.let { priceList ->
                savePriceList(
                    priceList.apply {
                        unitDto.status?.id?.let {
                            this.status = statusService.findById(it) as StatusEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД товара")

                        unitDto.model?.id?.let {
                            this.model = modelService.findById(it) as ModelEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД модели изготовителя")

                        unitDto.price?.let { price -> this.price = price }

                    }
                )
            }
        } ?: savePriceList(unitDto.toEntity())

    fun savePriceList(unit: Unit): Unit =
        unitJpaRepository.save(unit as UnitEntity)

    fun deleteById(unitEntityId: UUID) =
        unitJpaRepository.deleteById(unitEntityId)

    fun findAll(): List<Unit> =
        unitJpaRepository.findAll()

    fun findById(unitId: UUID): Unit =
        unitJpaRepository.findByIdOrNull(unitId)
            ?: throw EntityNotFoundException("Запись заказа с УИД $unitId не найдена")
}