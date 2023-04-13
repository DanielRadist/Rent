package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.rent.RentEntity
import java.util.*

object RentMockFactory {
    fun createRent(
        name: String = "Test",
        relaxed: Boolean = true
    ): RentEntity =
        mockk(relaxed = relaxed) client@{
            every { this@client.id } returns UUID.randomUUID()
            every { this@client.name } returns name
            every { this@client.listUnits } returns listOf()
        }
}