package ru.rent.app.status

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.rent.data.status.StatusEntity
import ru.rent.data.status.StatusJpaRepository
import ru.rent.domain.status.Status
import ru.rent.web.status.StatusDto
import ru.rent.web.status.StatusMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class StatusService(
    private val statusJpaRepository: StatusJpaRepository
) {
    fun save(statusDto: StatusDto): Status =
        statusDto.id?.let {
            statusJpaRepository.findByIdOrNull(it)?.let { product ->
                saveProduct(
                    product.apply {
                        statusDto.name?.let { name -> this.name = name }
                    }
                )
            }
        } ?: saveProduct(statusDto.toEntity())


    fun saveProduct(status: Status): Status =
        statusJpaRepository.save(status as StatusEntity)

    fun deleteById(productId: UUID) =
        statusJpaRepository.deleteById(productId)

    fun findAll(): List<Status> =
        statusJpaRepository.findAll()

    fun findById(productId: UUID): Status =
        statusJpaRepository.findByIdOrNull(productId)
            ?: throw EntityNotFoundException("Товар с УИД $productId не найден")
}