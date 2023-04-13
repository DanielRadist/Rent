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
import ru.rent.app.status.StatusService
import ru.rent.data.status.StatusEntity
import ru.rent.data.status.StatusJpaRepository
import ru.rent.domain.status.Status
import ru.rent.factory.ProductMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class StatusServiceTest {
    @InjectMockKs
    lateinit var statusService: StatusService

    @MockK
    lateinit var statusJpaRepository: StatusJpaRepository

    @Test
    fun `find by id`() {
        val product = ProductMockFactory.createProduct()
        val id: UUID = product.id

        every { statusJpaRepository.findByIdOrNull(id) } returns product

        assertDoesNotThrow { statusService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { statusJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { statusService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val status: Status = StatusEntity(name = "status")
        every { statusJpaRepository.save(status as StatusEntity) } returns ProductMockFactory.createProduct()

        assertDoesNotThrow { statusService.saveProduct(status) }
    }

    @Test
    fun `find all`() {
        every { statusJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { statusService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { statusService.deleteById(id) } returns Unit

        assertDoesNotThrow { statusService.deleteById(id) }
    }
}