package ru.rent.web.unit

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.rent.app.unit.UnitService
import ru.rent.web.unit.UnitMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/unit")
class UnitController(
    private val unitService: UnitService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") priceListId: UUID) =
        unitService.findById(priceListId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<UnitDto> =
        unitService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        unitService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody unitDto: UnitDto): UnitDto =
        unitService.save(unitDto).toDto()
}