package ru.rent.app.listunit

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.rent.app.unit.UnitService
import ru.rent.data.rent.RentEntity
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.data.listunit.ListUnitJpaRepository
import ru.rent.data.unit.UnitEntity
import ru.rent.domain.listunit.ListUnit
import ru.rent.web.listunit.ListUnitDto
import ru.rent.web.listunit.ListUnitMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ListUnitService(
        private val listUnitJpaRepository: ListUnitJpaRepository,
        private val unitService: ru.rent.app.unit.UnitService,
        private val rentService: ru.rent.app.rent.RentService
) {
    fun save(listUnitDto: ListUnitDto): ListUnit =
        listUnitDto.id?.let {
            listUnitJpaRepository.findByIdOrNull(it)?.let { order ->
                saveOrder(
                    order.apply {

                        listUnitDto.unit?.id?.let {
                            this.unit = unitService.findById(it) as UnitEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД списка товаров")

                        listUnitDto.rent?.id?.let {
                            this.rent = rentService.findById(it) as RentEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД клиента")
                    }
                )
            }
        } ?: saveOrder(listUnitDto.toEntity())

    fun saveOrder(listUnit: ListUnit): ListUnit =
        listUnitJpaRepository.save(listUnit as ListUnitEntity)

    fun deleteById(orderId: UUID) =
        listUnitJpaRepository.deleteById(orderId)

    fun findAll(): List<ListUnit> =
        listUnitJpaRepository.findAll()

    fun findById(orderId: UUID): ListUnit =
        listUnitJpaRepository.findByIdOrNull(orderId)
            ?: throw EntityNotFoundException("Заказ с УИД $orderId not found")
}