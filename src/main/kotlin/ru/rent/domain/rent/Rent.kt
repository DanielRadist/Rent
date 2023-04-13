package ru.rent.domain.rent

import ru.rent.domain.listunit.ListUnit
import java.util.*

/** Аренда */
interface Rent {
    /** УИД Аренды */
    val id: UUID

    /** Номер договора */
    val name: String

    /** Список оборудования */
    val listUnits: List<ListUnit>
}