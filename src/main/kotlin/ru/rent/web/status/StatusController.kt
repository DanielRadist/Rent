package ru.rent.web.status

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.rent.app.status.StatusService
import ru.rent.web.status.StatusMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/status")
class StatusController(
    private val statusService: StatusService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") productId: UUID) =
        statusService.findById(productId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<StatusDto> =
        statusService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        statusService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody statusDto: StatusDto): StatusDto =
        statusService.save(statusDto).toDto()
}