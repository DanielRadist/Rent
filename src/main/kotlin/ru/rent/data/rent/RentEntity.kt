package ru.rent.data.rent

import com.fasterxml.jackson.annotation.JsonIgnore
import ru.rent.data.listunit.ListUnitEntity
import ru.rent.domain.rent.Rent
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "public", name = "\"Rent\"")
class RentEntity(
    @Id
    @Column(name = "\"RentId\"")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "\"Name\"")
    override var name: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "\"RentId\"")
    @JsonIgnore
    override val listUnits: List<ListUnitEntity> = listOf()
) : Rent
