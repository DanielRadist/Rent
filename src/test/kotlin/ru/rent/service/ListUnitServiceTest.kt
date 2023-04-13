package ru.rent.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import ru.rent.app.listunit.ListUnitService
import ru.rent.data.rent.RentEntity
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.data.listunit.ListUnitJpaRepository
import ru.rent.data.unit.UnitEntity
import ru.rent.data.status.StatusEntity
import ru.rent.domain.listunit.ListUnit
import ru.rent.factory.ListUnitMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ListUnitServiceTest {
    @InjectMockKs
    lateinit var listUnitService: ListUnitService

    @MockK
    lateinit var listUnitJpaRepository: ListUnitJpaRepository

    @Test
    fun `find by id`() {
        val order = ListUnitMockFactory.createListUnit()
        val id: UUID = order.id

        every { listUnitJpaRepository.findByIdOrNull(id) } returns order

        assertDoesNotThrow { listUnitService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { listUnitJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { listUnitService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val rent = RentEntity(name = "rent")
        val status = StatusEntity(name = "status")
        val manufacturer = ManufacturerEntity(name = "name", country = "c")
        val model = ModelEntity(name = "model", manufacturer = manufacturer)
        val priceList = UnitEntity(status = status, model = model, price = 1)
        val listUnitEntity: ListUnit = ListUnitEntity(unit = priceList, rent = rent)
        every { listUnitJpaRepository.save(listUnitEntity as ListUnitEntity) } returns ListUnitMockFactory.createListUnit()

        assertDoesNotThrow { listUnitService.saveOrder(listUnitEntity) }
    }

    @Test
    fun `find all`() {
        every { listUnitJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { listUnitService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { listUnitService.deleteById(id) } returns Unit

        assertDoesNotThrow { listUnitService.deleteById(id) }
    }
}