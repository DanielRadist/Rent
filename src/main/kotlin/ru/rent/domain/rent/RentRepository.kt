package ru.rent.domain.rent

import java.util.*

/** Интерфейс репозитория для работы с покупателями */
interface RentRepository {
    fun findAll(): List<Rent>

    fun findById(id: UUID): Rent

    fun save(client: Rent): Unit

    fun update(client: Rent): Unit

    fun deleteById(id: UUID): Unit
}