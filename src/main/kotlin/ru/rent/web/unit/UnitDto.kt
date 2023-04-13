package ru.rent.web.unit

import ru.rent.web.model.ModelDto
import ru.rent.web.status.StatusDto
import java.util.*

class UnitDto(
        val id: UUID? = null,
        val status: StatusDto? = null,
        val model: ModelDto? = null,
        val price: Long? = null
)