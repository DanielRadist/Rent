package ru.rent.data.listunit

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ListUnitJpaRepository : JpaRepository<ListUnitEntity, UUID>