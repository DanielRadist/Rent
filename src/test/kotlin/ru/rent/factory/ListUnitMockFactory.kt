package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.listunit.ListUnitEntity
import java.util.*

object ListUnitMockFactory {
    fun createListUnit(
        clientName: String = "ClientTestName",
        relaxed: Boolean = true
    ): ListUnitEntity =
        mockk(relaxed = relaxed) listunit@{
            every { this@listunit.id } returns UUID.randomUUID()
            every { this@listunit.unit } returns PriceListMockFactory.createPriceList()
            every { this@listunit.rent } returns RentMockFactory.createRent(name = clientName)
        }
}