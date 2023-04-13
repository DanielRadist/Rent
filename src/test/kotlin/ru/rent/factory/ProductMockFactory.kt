package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.status.StatusEntity
import java.util.*

object ProductMockFactory {
    fun createProduct(
        name: String = "ProductTestName",
        relaxed: Boolean = true
    ): StatusEntity =
        mockk(relaxed = relaxed) product@{
            every { this@product.id } returns UUID.randomUUID()
            every { this@product.name } returns name
            every { this@product.unit } returns listOf()
        }
}