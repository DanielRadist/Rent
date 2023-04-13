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
import ru.rent.data.manufacturer.ManufacturerJpaRepository
import ru.rent.data.model.ModelJpaRepository
import ru.rent.data.unit.UnitEntity
import ru.rent.data.unit.UnitJpaRepository
import ru.rent.data.status.StatusJpaRepository
import ru.rent.factory.PriceListMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class UnitRepositoryTest {
    @Autowired
    lateinit var unitJpaRepository: UnitJpaRepository
    @Autowired
    lateinit var statusJpaRepository: StatusJpaRepository
    @Autowired
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository
    @Autowired
    lateinit var modelJpaRepository: ModelJpaRepository

    private lateinit var firstPriceList: UnitEntity
    private lateinit var secondPriceList: UnitEntity

    @BeforeEach
    private fun setup() {
        firstPriceList = PriceListMockFactory.createPriceList(
            price = 1
        )
        secondPriceList = PriceListMockFactory.createPriceList(
            price = 2
        )

        statusJpaRepository.save(firstPriceList.status)
        modelJpaRepository.save(firstPriceList.model)

        statusJpaRepository.save(secondPriceList.status)
        modelJpaRepository.save(secondPriceList.model)
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { unitJpaRepository.save(firstPriceList) }
        val foundPriceList: UnitEntity = unitJpaRepository.findById(firstPriceList.id).get()

        assertEquals(firstPriceList.id, foundPriceList.id)
        assertEquals(firstPriceList.price, foundPriceList.price)
        assertEquals(firstPriceList.listUnits.size, foundPriceList.listUnits.size)
        assertEquals(firstPriceList.status.id, foundPriceList.status.id)
        assertEquals(firstPriceList.status.name, foundPriceList.status.name)
        assertEquals(firstPriceList.model.id, foundPriceList.model.id)
        assertEquals(firstPriceList.model.name, foundPriceList.model.name)
    }

    @Test
    fun `delete by id`() {
        unitJpaRepository.save(firstPriceList)

        assertDoesNotThrow { unitJpaRepository.deleteById(firstPriceList.id) }
    }

    @Test
    fun `throw when delete another id`() {
        val primaryId: UUID = firstPriceList.id
        val anotherId: UUID = UUID.randomUUID()
        unitJpaRepository.save(firstPriceList)
        every { firstPriceList.id } returns anotherId

        assertThrows<EmptyResultDataAccessException> { unitJpaRepository.deleteById(firstPriceList.id) }
    }

    @Test
    fun `find all`() {
        unitJpaRepository.deleteAll()
        unitJpaRepository.save(firstPriceList)
        unitJpaRepository.save(secondPriceList)
        val actualPriceLists: List<UnitEntity> = listOf(firstPriceList, secondPriceList)

        var priceLists: List<UnitEntity> = listOf()
        assertDoesNotThrow { priceLists = unitJpaRepository.findAll() }
        for ((index, priceList) in priceLists.withIndex())
            assertEquals(priceList.id, actualPriceLists[index].id)
    }

    @Test
    fun `find by id`() {
        unitJpaRepository.save(firstPriceList)
        var foundPriceList: UnitEntity? = null
        assertDoesNotThrow { foundPriceList = unitJpaRepository.findById(firstPriceList.id).get() }

        assertEquals(firstPriceList.id, foundPriceList?.id)
        assertEquals(firstPriceList.price, foundPriceList?.price)
        assertEquals(firstPriceList.model.id, foundPriceList?.model?.id)
    }

    @Test
    fun `throw when find by another id`() {
        unitJpaRepository.save(firstPriceList)
        val primaryId: UUID = firstPriceList.id
        val anotherId: UUID = UUID.randomUUID()
        every { firstPriceList.id } returns anotherId

        assertThrows<NoSuchElementException> { unitJpaRepository.findById(firstPriceList.id).get() }
    }

    @Test
    fun `update entity`() {
        val primaryPrice = firstPriceList.price
        val changedPrice: Long = 11
        every { firstPriceList.price } returns changedPrice

        assertDoesNotThrow { unitJpaRepository.save(firstPriceList) }
        assertNotEquals(primaryPrice, firstPriceList.price)
        assertEquals(changedPrice, firstPriceList.price)
    }
}