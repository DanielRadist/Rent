package ru.rent.repository

import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional
import ru.rent.data.rent.RentJpaRepository
import ru.rent.data.manufacturer.ManufacturerJpaRepository
import ru.rent.data.model.ModelJpaRepository
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.data.listunit.ListUnitJpaRepository
import ru.rent.data.unit.UnitJpaRepository
import ru.rent.data.status.StatusJpaRepository
import ru.rent.factory.ListUnitMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ListUnitRepositoryTest {
    @Autowired
    lateinit var listUnitJpaRepository: ListUnitJpaRepository
    @Autowired
    lateinit var rentJpaRepository: RentJpaRepository
    @Autowired
    lateinit var unitJpaRepository: UnitJpaRepository
    @Autowired
    lateinit var modelJpaRepository: ModelJpaRepository
    @Autowired
    lateinit var statusJpaRepository: StatusJpaRepository

    private lateinit var firstOrder: ListUnitEntity
    private lateinit var secondOrder: ListUnitEntity

    @BeforeEach
    private fun setup() {
        firstOrder = ListUnitMockFactory.createListUnit(
            clientName = "FirstOrderClientTestName"
        )
        secondOrder = ListUnitMockFactory.createListUnit(
            clientName = "SecondOrderClientTestName"
        )

        statusJpaRepository.save(firstOrder.unit.status)
        modelJpaRepository.save(firstOrder.unit.model)
        unitJpaRepository.save(firstOrder.unit)
        rentJpaRepository.save(firstOrder.rent)

        statusJpaRepository.save(secondOrder.unit.status)
        modelJpaRepository.save(secondOrder.unit.model)
        unitJpaRepository.save(secondOrder.unit)
        rentJpaRepository.save(secondOrder.rent)
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { listUnitJpaRepository.save(firstOrder) }
        val foundOrder: ListUnitEntity = listUnitJpaRepository.findById(firstOrder.id).get()

        assertEquals(foundOrder.rent.id, firstOrder.rent.id)
        assertEquals(foundOrder.unit.id, firstOrder.unit.id)
        assertEquals(foundOrder.unit.model.id, firstOrder.unit.model.id)
        assertEquals(foundOrder.unit.status.id, firstOrder.unit.status.id)
    }

    @Test
    fun `delete by id`() {
        listUnitJpaRepository.save(firstOrder)

        assertDoesNotThrow { listUnitJpaRepository.deleteById(firstOrder.id) }
    }

    @Test
    fun `throw when delete another id`() {
        val primaryId: UUID = firstOrder.id
        val anotherId: UUID = UUID.randomUUID()
        listUnitJpaRepository.save(firstOrder)
        every { firstOrder.id } returns anotherId

        assertNotEquals(primaryId, anotherId)
        assertThrows<EmptyResultDataAccessException> { listUnitJpaRepository.deleteById(firstOrder.id) }
    }

    @Test
    fun `find all`() {
        listUnitJpaRepository.deleteAll()
        listUnitJpaRepository.save(firstOrder)
        listUnitJpaRepository.save(secondOrder)
        val actualOrders: List<ListUnitEntity> = listOf(firstOrder, secondOrder)

        var orders: List<ListUnitEntity> = listOf()
        assertDoesNotThrow { orders = listUnitJpaRepository.findAll() }
        assertEquals(orders.size, actualOrders.size)
        for ((index, order) in orders.withIndex())
            assertEquals(order.id, actualOrders[index].id)
    }

    @Test
    fun `find by id`() {
        listUnitJpaRepository.save(firstOrder)
        var foundOrder: ListUnitEntity? = null

        assertDoesNotThrow { foundOrder = listUnitJpaRepository.findById(firstOrder.id).get() }
        assertNotNull(foundOrder)

        assertEquals(foundOrder?.rent?.id, firstOrder.rent.id)
        assertEquals(foundOrder?.unit?.id, firstOrder.unit.id)
        assertEquals(foundOrder?.unit?.model?.id, firstOrder.unit.model.id)
        assertEquals(foundOrder?.unit?.status?.id, firstOrder.unit.status.id)
    }

    @Test
    fun `throw when find by another id`() {
        val actualId: UUID = firstOrder.id
        val anotherId: UUID = UUID.randomUUID()
        listUnitJpaRepository.save(firstOrder)
        every { firstOrder.id } returns anotherId

        assertNotEquals(actualId, anotherId)
        assertThrows<NoSuchElementException> { listUnitJpaRepository.findById(firstOrder.id).get() }
    }

    @Test
    fun `update entity`() {
        listUnitJpaRepository.save(firstOrder)
        val foundOrder: ListUnitEntity = listUnitJpaRepository.findById(firstOrder.id).get()
    }
}