package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.manufacturer.ManufacturerEntity
import java.util.*

object ManufacturerMockFactory {
    fun createManufacturer(
        name: String = "SomeTestName",
        country: String = "SomeTestCountry",
        site: String? = null,
        relaxed: Boolean = true
    ): ManufacturerEntity =
        mockk(relaxed = relaxed) manufacturer@{
            every { this@manufacturer.id } returns UUID.randomUUID()
            every { this@manufacturer.name } returns name
            every { this@manufacturer.country } returns country
            every { this@manufacturer.site } returns site
            every { this@manufacturer.unit } returns listOf()
            every { this@manufacturer.models } returns listOf()
        }
}