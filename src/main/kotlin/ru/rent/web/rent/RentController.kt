package ru.rent.web.rent

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.rent.web.rent.RentMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/rent")
class RentController(
    private val rentService: ru.rent.app.rent.RentService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") clientId: UUID) =
        rentService.findById(clientId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<RentDto> =
        rentService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        rentService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody rentDto: RentDto): RentDto =
        rentService.save(rentDto).toDto()
}