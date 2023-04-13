package ru.rent.app.rent

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.rent.data.rent.RentEntity
import ru.rent.data.rent.RentJpaRepository
import ru.rent.domain.rent.Rent
import ru.rent.web.rent.RentDto
import ru.rent.web.rent.RentMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class RentService(
    private val rentJpaRepository: RentJpaRepository
) {
    fun save(rentDto: RentDto): Rent =
        rentDto.id?.let {
            rentJpaRepository.findByIdOrNull(it)?.let { rent ->
                saveRent(
                    rent.apply {
                        rentDto.name?.let { name -> this.name = name }
                    }
                )
            }
        } ?: saveRent(rentDto.toEntity())

    fun saveRent(rent: Rent): Rent =
        rentJpaRepository.save(rent as RentEntity)

    fun deleteById(rentId: UUID) =
        rentJpaRepository.deleteById(rentId)

    fun findById(rentId: UUID): Rent =
        rentJpaRepository.findByIdOrNull(rentId)
            ?: throw EntityNotFoundException("Аренда с УИД $rentId не найден")

    fun findAll(): List<Rent> =
        rentJpaRepository.findAll()
}