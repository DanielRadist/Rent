package ru.rent.domain.listunit

import ru.rent.data.unit.UnitEntity
import ru.rent.domain.rent.Rent
import java.util.*

/** Список оборудования */
interface ListUnit {
    /** УИД заказа */
    val id: UUID

    /** Оборудование */
    val unit: UnitEntity

    /** Аренда */
    val rent: Rent
}