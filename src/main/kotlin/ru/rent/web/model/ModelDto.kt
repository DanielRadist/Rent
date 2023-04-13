package ru.rent.web.model

import ru.rent.web.manufacturer.ManufacturerDto
import java.util.*

class ModelDto(
    val id: UUID? = null,
    val name: String? = null,
    val manufacturer: ManufacturerDto? = null
)