package ru.rent.domain.status

import java.util.*

/** Состояние */
interface Status {
    /** УИД Состояния */
    val id: UUID

    /** Наименование */
    val name: String

    /** Список оборудования */
    val unit: List<ru.rent.domain.unit.Unit>
}