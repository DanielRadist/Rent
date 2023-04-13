package ru.rent.domain.model

import ru.rent.domain.unit.Unit
import ru.rent.domain.manufacturer.Manufacturer
import java.util.*

/** Модель оборудования */
interface Model {
    /** УИД модели */
    val id: UUID

    /** Наименование */
    val name: String

    /** Производитель */
    val manufacturer: Manufacturer

    /** Список оборудования */
    val unit: List<Unit>
}