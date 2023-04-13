package ru.rent.data.status

import ru.rent.data.unit.UnitEntity
import ru.rent.domain.status.Status
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Status\"")
class StatusEntity(
    @Id
    @Column(name = "\"StatusId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override var name: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"UnitId\"")
    override val unit: List<UnitEntity> = listOf()
) : Status