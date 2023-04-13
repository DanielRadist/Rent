package ru.rent.data.unit

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UnitJpaRepository : JpaRepository<UnitEntity, UUID> {
    @EntityGraph(
        attributePaths = ["status", "manufacturer", "model"]
    )
    override fun findById(id: UUID): Optional<UnitEntity>
}