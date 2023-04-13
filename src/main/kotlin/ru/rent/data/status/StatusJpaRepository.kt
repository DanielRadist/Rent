package ru.rent.data.status

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StatusJpaRepository : JpaRepository<StatusEntity, UUID>