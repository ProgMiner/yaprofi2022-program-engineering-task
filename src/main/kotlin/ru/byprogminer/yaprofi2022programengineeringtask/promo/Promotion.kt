package ru.byprogminer.yaprofi2022programengineeringtask.promo

import javax.persistence.*


@Entity(name = "Promotion")
@Table(name = "promotion")
open class Promotion(

    @Id @GeneratedValue
    open val id: Long?,

    @Column(nullable = false)
    open var name: String?,

    open var description: String?,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
    open var prizes: MutableSet<Prize> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
    open var participants: MutableSet<Participant> = mutableSetOf(),
) {

    constructor(): this(null, null, null)
    constructor(name: String, description: String?): this(null, name, description)
}
