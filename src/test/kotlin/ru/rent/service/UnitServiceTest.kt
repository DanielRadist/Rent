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
import ru.rent.app.unit.UnitService
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.unit.UnitEntity
import ru.rent.data.unit.UnitJpaRepository
import ru.rent.data.status.StatusEntity
import ru.rent.domain.unit.Unit
import ru.rent.factory.PriceListMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class UnitServiceTest {
    @InjectMockKs
    lateinit var unitService: UnitService

    @MockK
    lateinit var unitJpaRepository: UnitJpaRepository

    @Test
    fun `find by id`() {
        val priceList = PriceListMockFactory.createPriceList()
        val id: UUID = priceList.id

        every { unitJpaRepository.findByIdOrNull(id) } returns priceList

        assertDoesNotThrow { unitService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { unitJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { unitService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val product = StatusEntity(name = "status")
        val manufacturer = ManufacturerEntity(name = "name", country = "c")
        val model = ModelEntity(name = "model", manufacturer = manufacturer)
        val unitEntity: Unit = UnitEntity(status = product, model = model, price = 1)
        every { unitJpaRepository.save(unitEntity as UnitEntity) } returns PriceListMockFactory.createPriceList()

        assertDoesNotThrow { unitService.savePriceList(unitEntity) }
    }

    @Test
    fun `find all`() {
        every { unitJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { unitService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { unitService.deleteById(id) } returns Unit

        assertDoesNotThrow { unitService.deleteById(id) }
    }
}