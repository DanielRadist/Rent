package ru.rent.repository

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import ru.rent.data.rent.RentEntity
import ru.rent.data.rent.RentJpaRepository
import ru.rent.data.rent.RentRepositoryImpl
import ru.rent.domain.rent.Rent
import ru.rent.factory.RentMockFactory
import ru.rent.web.rent.RentDto
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class RentRepositoryTest {
    @Autowired
    lateinit var clientRepositoryImpl: RentRepositoryImpl
    @Autowired
    lateinit var rentJpaRepository: RentJpaRepository

    private lateinit var firstClient: RentEntity
    private lateinit var secondClient: RentEntity

    @BeforeEach
    private fun setup() {
        firstClient = RentMockFactory.createRent(
            name = "FirstRentTests",
            relaxed = true
        )
        secondClient = RentMockFactory.createRent(
            name = "SecondRentTests",
            relaxed = true
        )
    }

    @Test
    fun `save full entity test`() {
        clientRepositoryImpl.save(firstClient)
        var savedClient: RentEntity? = null

        assertDoesNotThrow { savedClient = rentJpaRepository.findById(firstClient.id).orElseThrow() }
        assertEquals(firstClient.id, savedClient?.id)
        assertEquals(firstClient.name, savedClient?.name)
    }

    @Test
    fun `throw when save empty client dto`() {
        var rentDto = mockk<RentDto>(relaxed = true) dto@{
            every { this@dto.id } returns null
            every { this@dto.name } returns null
        }

        assertThrows<TypeCastException> {
            clientRepositoryImpl.save(rentDto as? RentEntity ?: throw TypeCastException())
        }
    }

    @Test
    fun `delete by Id`() {
        val clientsInDatabase = rentJpaRepository.findAll().size

        rentJpaRepository.save(firstClient)

        assertDoesNotThrow { clientRepositoryImpl.deleteById(firstClient.id) }

        val clients: List<RentEntity> = rentJpaRepository.findAll()
        assertEquals(clients.size, clientsInDatabase)
    }

    @Test
    fun `throw when delete by empty id`() {
        val id: UUID? = null

        assertThrows<Exception> { clientRepositoryImpl.deleteById(id ?: throw Exception()) }
    }

    @Test
    fun `find all entity`() {
        rentJpaRepository.deleteAll()
        rentJpaRepository.save(firstClient)
        rentJpaRepository.save(secondClient)
        val jpaClients: List<RentEntity> = rentJpaRepository.findAll()
        var checkClients: List<Rent>? = null

        assertDoesNotThrow { checkClients = clientRepositoryImpl.findAll() }
        checkClients?.let {
            // Ожидаем, что списки приходят одинаковые
            for ((index, checkClient) in it.withIndex()) {
                assertEquals(jpaClients[index].id, checkClient.id)
                assertEquals(jpaClients[index].name, checkClient.name)
            }
        }
    }

    @Test
    fun `find by id`() {
        val id: UUID = firstClient.id
        rentJpaRepository.save(firstClient)
        var findClient: Rent? = null

        assertDoesNotThrow { findClient = clientRepositoryImpl.findById(id) }
        assertEquals(findClient?.id, firstClient.id)
        assertEquals(findClient?.name, firstClient.name)
    }

    @Test
    fun `throw when find by empty id`() {
        val id: UUID? = null

        assertThrows<Exception> { clientRepositoryImpl.findById(id ?: throw Exception()) }
    }

    @Test
    @Transactional
    fun `update entity`() {
        rentJpaRepository.save(firstClient)
        val changedName: String = "ChangedNameTest"
        every { firstClient.name } returns changedName

        assertDoesNotThrow { clientRepositoryImpl.update(firstClient) }
        var changedClient: Rent? = null
        assertDoesNotThrow { changedClient = rentJpaRepository.findById(firstClient.id).get() }
        assertEquals(changedClient?.id!!, firstClient.id)
        assertEquals(changedClient?.name!!, firstClient.name)
    }
}