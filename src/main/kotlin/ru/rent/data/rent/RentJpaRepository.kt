package ru.rent.data.rent

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RentJpaRepository : JpaRepository<RentEntity, UUID> {
    @Query(
        nativeQuery = true,
        value = "SELECT * FROM public.\"Rent\""
    )
    fun nativeFindAll(): List<RentEntity>

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM public.\"Rent\" c WHERE c.\"RentId\" = :id"
    )
    fun nativeFindById(@Param(value = "id") id: UUID): RentEntity

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "INSERT INTO public.\"Rent\" (\"RentId\", \"Name\") " +
                "VALUES (:rentId, :rentName)"
    )
    fun nativeSave(
        @Param(value = "rentId") id: UUID,
        @Param(value = "rentName") name: String
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "UPDATE public.\"Rent\" SET \"Name\" = :rentName WHERE \"RentId\" = :rentId"
    )
    fun nativeUpdate(
        @Param(value = "rentId") id: UUID,
        @Param(value = "rentName") name: String
    )

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        nativeQuery = true,
        value = "DELETE FROM public.\"Rent\" WHERE \"RentId\" = :id"
    )
    fun nativeDeleteById(@Param(value = "id") id: UUID)
}