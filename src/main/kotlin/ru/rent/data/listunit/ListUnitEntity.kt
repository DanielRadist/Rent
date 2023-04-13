package ru.rent.data.listunit

import org.hibernate.annotations.DynamicUpdate
import ru.rent.data.rent.RentEntity
import ru.rent.data.unit.UnitEntity
import ru.rent.domain.listunit.ListUnit
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"ListUnit\"")
@DynamicUpdate
class ListUnitEntity(
        @Id
    @Column(name = "\"ListUnitId\"")
    override val id: UUID = UUID.randomUUID(),

        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"UnitId\"")
    override var unit: UnitEntity,


        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"RentId\"")
    override var rent: RentEntity
) : ListUnit