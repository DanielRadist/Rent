package ru.rent.web.listunit

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.rent.app.listunit.ListUnitService
import ru.rent.web.listunit.ListUnitMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/listunit")
class ListUnitController(
    private val listUnitService: ListUnitService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") orderId: UUID) =
        listUnitService.findById(orderId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<ListUnitDto> =
        listUnitService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") rentId: UUID) =
        listUnitService.deleteById(rentId)

    @PostMapping
    @Transactional
    fun save(@RequestBody listUnitDto: ListUnitDto): ListUnitDto =
        listUnitService.save(listUnitDto).toDto()
}