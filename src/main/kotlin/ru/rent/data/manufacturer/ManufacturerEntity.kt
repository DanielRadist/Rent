package ru.rent.data.manufacturer

import ru.rent.data.model.ModelEntity
import ru.rent.data.unit.UnitEntity
import ru.rent.domain.manufacturer.Manufacturer
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Manufacturer\"")
class ManufacturerEntity(
        @Id
    @Column(name = "\"ManufacturerId\"")
    override val id: UUID = UUID.randomUUID(),

        @Column(name = "\"Name\"")
    override var name: String,

        @Column(name = "\"Country\"")
    override var country: String,

        @Column(name = "\"Site\"")
    override var site: String? = null,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ManufacturerId\"")
    override val unit: List<UnitEntity> = listOf(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"ManufacturerId\"")
    override val models: List<ModelEntity> = listOf()
) : Manufacturer