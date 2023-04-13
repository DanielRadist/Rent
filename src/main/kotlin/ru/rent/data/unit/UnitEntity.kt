package ru.rent.data.unit

import org.hibernate.annotations.DynamicUpdate
import ru.rent.data.status.StatusEntity
import ru.rent.data.model.ModelEntity
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.domain.unit.Unit
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Unit\"")
@DynamicUpdate
class UnitEntity(
        @Id
    @Column(name = "\"UnitId\"")
    override val id: UUID = UUID.randomUUID(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"UnitId\"")
    override val listUnits: List<ListUnitEntity> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"StatusId\"")
    override var status: StatusEntity,

        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ModelId\"")
    override var model: ModelEntity,

        @Column(name = "\"Price\"")
    override var price: Long,

) : Unit