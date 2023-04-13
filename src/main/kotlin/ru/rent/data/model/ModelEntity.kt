package ru.rent.data.model

import org.hibernate.annotations.DynamicUpdate
import ru.rent.data.manufacturer.ManufacturerEntity
import ru.rent.data.unit.UnitEntity
import ru.rent.domain.model.Model
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Model\"")
@DynamicUpdate
class ModelEntity(
        @Id
    @Column(name = "\"ModelId\"")
    override val id: UUID = UUID.randomUUID(),

        @Column(name = "\"Name\"")
    override var name: String,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ModelId\"")
    override val unit: List<UnitEntity> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ManufacturerId\"")
    override var manufacturer: ManufacturerEntity
) : Model