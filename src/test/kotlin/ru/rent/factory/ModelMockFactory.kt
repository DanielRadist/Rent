package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.model.ModelEntity
import java.util.*

object ModelMockFactory {
    fun createModel(
        name: String = "SomeTestModel",
        manufacturerName: String = "SomeManufacturerNameTestModel",
        relaxed: Boolean = true
    ): ModelEntity =
        mockk(relaxed = relaxed) model@{
            every { this@model.id } returns UUID.randomUUID()
            every { this@model.name } returns name
            every { this@model.unit } returns listOf()
            every { this@model.manufacturer } returns ManufacturerMockFactory.createManufacturer(
                name = manufacturerName
            )
        }
}