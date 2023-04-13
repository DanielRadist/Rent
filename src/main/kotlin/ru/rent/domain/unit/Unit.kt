package ru.rent.domain.`unit`

import ru.rent.domain.status.Status
import ru.rent.domain.manufacturer.Manufacturer
import ru.rent.domain.model.Model
import ru.rent.domain.listunit.ListUnit
import java.util.*

/** Оборудование */
interface Unit {
    /** УИД оборудования */
    val id: UUID

    /** Список оборудования */
    val listUnits: List<ListUnit>

    /** Состояние */
    val status: Status

    /** Модель производителя */
    val model: Model

    /** Цена аренды */
    val price: Long

}