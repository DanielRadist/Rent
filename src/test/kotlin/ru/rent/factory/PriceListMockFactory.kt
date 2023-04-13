package ru.rent.factory

import io.mockk.every
import io.mockk.mockk
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.unit.UnitEntity
import java.util.*

object PriceListMockFactory {
    fun createPriceList(
        price: Long = 0,
        relaxed: Boolean = true
    ): UnitEntity {
        val manufacturer: ManufacturerEntity = ManufacturerMockFactory.createManufacturer()
        val model: ModelEntity = ModelMockFactory.createModel().also {
            every { it.manufacturer } returns manufacturer
        }

        return mockk(relaxed = relaxed) priceList@{
            every { this@priceList.id } returns UUID.randomUUID()
            every { this@priceList.listUnits } returns listOf()
            every { this@priceList.status } returns ProductMockFactory.createProduct()
            every { this@priceList.model } returns model
            every { this@priceList.price } returns price
        }
    }

}