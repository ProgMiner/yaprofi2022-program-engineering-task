package ru.byprogminer.yaprofi2022programengineeringtask.promo

import javax.persistence.*


@Entity(name = "Participant")
@Table(name = "participant")
open class Participant(

    @Id @GeneratedValue
    open val id: Long?,

    @Column(nullable = false)
    open var name: String?,

    @ManyToOne
    @JoinColumn(nullable = false)
    open var promotion: Promotion?,
) {

    constructor(): this(null, null, null)
    constructor(name: String): this(null, name, null)
}
