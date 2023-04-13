package ru.rent.web.listunit

import java.util.*
import ru.rent.web.rent.RentDto
import ru.rent.web.unit.UnitDto

class ListUnitDto(
    val id: UUID? = null,
    val unit: UnitDto? = null,
    val rent: RentDto? = null
)