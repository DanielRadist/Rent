package ru.rent.data.rent

import org.springframework.stereotype.Repository
import ru.rent.domain.rent.Rent
import ru.rent.domain.rent.RentRepository
import java.util.*

@Repository
class RentRepositoryImpl(
    private val rentJpaRepository: RentJpaRepository
) : RentRepository {
    override fun findAll(): List<Rent> =
        rentJpaRepository.nativeFindAll()

    override fun findById(id: UUID): Rent =
        rentJpaRepository.nativeFindById(id)

    override fun save(client: Rent) {
        rentJpaRepository.nativeSave(
            id = client.id,
            name = client.name
        )
    }

    override fun update(client: Rent) {
        rentJpaRepository.nativeUpdate(
            id = client.id,
            name = client.name
        )
    }

    override fun deleteById(id: UUID) =
        rentJpaRepository.nativeDeleteById(id)
}