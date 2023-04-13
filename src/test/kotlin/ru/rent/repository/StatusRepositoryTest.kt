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
import ru.rent.data.status.StatusEntity
import ru.rent.data.status.StatusJpaRepository
import ru.rent.factory.ProductMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class StatusRepositoryTest {
    @Autowired
    lateinit var statusJpaRepository: StatusJpaRepository

    private lateinit var firstProduct: StatusEntity
    private lateinit var secondProduct: StatusEntity

    @BeforeEach
    private fun setup() {
        firstProduct = ProductMockFactory.createProduct(
            name = "FirstProductTestName"
        )
        secondProduct = ProductMockFactory.createProduct(
            name = "SecondProductTestName"
        )
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { statusJpaRepository.save(firstProduct) }
        val foundProduct: StatusEntity = statusJpaRepository.findById(firstProduct.id).get()

        assertEquals(firstProduct.id, foundProduct.id)
        assertEquals(firstProduct.name, foundProduct.name)
        assertEquals(firstProduct.unit.size, foundProduct.unit.size)

        statusJpaRepository.deleteAll()
    }

    @Test
    fun `delete by id`() {
        statusJpaRepository.save(firstProduct)
        val countProducts = statusJpaRepository.findAll().size

        assertDoesNotThrow { statusJpaRepository.deleteById(firstProduct.id) }
        assertEquals(countProducts - 1, statusJpaRepository.findAll().size)
    }

    @Test
    fun `throw when delete another id`() {
        statusJpaRepository.save(firstProduct)
        val primaryId: UUID = firstProduct.id
        val anotherId: UUID = UUID.randomUUID()
        every { firstProduct.id } returns anotherId

        assertNotEquals(firstProduct.id, primaryId)
        assertThrows<EmptyResultDataAccessException> { statusJpaRepository.deleteById(firstProduct.id) }

        statusJpaRepository.deleteAll()
    }

    @Test
    @Transactional
    fun `find all`() {
        statusJpaRepository.deleteAll()
        statusJpaRepository.save(firstProduct)
        statusJpaRepository.save(secondProduct)
        val actualProducts = listOf(firstProduct, secondProduct)

        var products: List<StatusEntity> = listOf()
        assertDoesNotThrow { products = statusJpaRepository.findAll() }
        assertEquals(actualProducts.size, products.size)
        for ((index, product) in products.withIndex()) {
            assertEquals(product.id, actualProducts[index].id)
            assertEquals(product.name, actualProducts[index].name)
        }

        statusJpaRepository.deleteAll()
    }

    @Test
    fun `find by id`() {
        statusJpaRepository.save(firstProduct)
        var product: StatusEntity? = null

        assertDoesNotThrow { product = statusJpaRepository.findById(firstProduct.id).get() }
        assertEquals(firstProduct.name, product?.name)
    }

    @Test
    fun `throw when find by another id`() {
        val primaryId: UUID = firstProduct.id
        val anotherId: UUID = UUID.randomUUID()
        statusJpaRepository.save(firstProduct)
        every { firstProduct.id } returns anotherId

        assertNotEquals(primaryId, anotherId)
        assertThrows<NoSuchElementException> { statusJpaRepository.findById(firstProduct.id).get() }
    }

    @Test
    fun `update entity`() {
        statusJpaRepository.save(firstProduct)
        val changedName: String = "ChangedNameTest"
        every { firstProduct.name } returns changedName

        assertDoesNotThrow { statusJpaRepository.save(firstProduct) }
        val product: StatusEntity = statusJpaRepository.findById(firstProduct.id).get()
        assertEquals(product.name, firstProduct.name)
    }
}