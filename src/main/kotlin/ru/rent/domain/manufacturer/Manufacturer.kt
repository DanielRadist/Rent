package ru.rent.domain.manufacturer

import ru.rent.domain.model.Model
import ru.rent.domain.unit.Unit
import java.util.*

/** Производитель */
interface Manufacturer {
    /** УИД производителя */
    val id: UUID

    /** Наименование */
    val name: String

    /** Страна производителя */
    val country: String

    /** Сайт производителя */
    val site: String?

    /** Список оборудования */
    val unit: List<Unit>

    /** Модели производителя */
    val models: List<Model>
}