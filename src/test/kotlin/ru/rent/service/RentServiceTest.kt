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
import ru.rent.data.rent.RentEntity
import ru.rent.data.rent.RentJpaRepository
import ru.rent.domain.rent.Rent
import ru.rent.factory.RentMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class RentServiceTest {
    @InjectMockKs
    lateinit var rentService: ru.rent.app.rent.RentService

    @MockK
    lateinit var rentJpaRepository: RentJpaRepository

    @Test
    fun `find by id`() {
        val client = RentMockFactory.createRent(name = "Bob")
        val id: UUID = client.id

        every { rentJpaRepository.findByIdOrNull(id) } returns client

        assertDoesNotThrow {
            val findingClient = rentService.findById(id)
            assertEquals(client.name, findingClient.name)
        }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { rentJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { rentService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val clientEntity: Rent = RentEntity(name = "Pop")
        every { rentJpaRepository.save(clientEntity as RentEntity) } returns RentMockFactory.createRent(
            name = clientEntity.name
        )

        assertDoesNotThrow {
            val client = rentService.saveRent(clientEntity as RentEntity)
            assertEquals(clientEntity.name, client.name)
        }
    }

    @Test
    fun `find all`() {
        every { rentJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { rentService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { rentService.deleteById(id) } returns Unit

        assertDoesNotThrow { rentService.deleteById(id) }
    }
}