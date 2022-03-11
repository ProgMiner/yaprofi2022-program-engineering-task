package ru.byprogminer.yaprofi2022programengineeringtask.promo

import javax.persistence.*


@Entity(name = "Prize")
@Table(name = "prize")
open class Prize(

    @Id @GeneratedValue
    open val id: Long?,

    @Column(nullable = false)
    open var description: String?,

    @ManyToOne
    @JoinColumn(nullable = false)
    open var promotion: Promotion?,
) {

    constructor(): this(null, null, null)
    constructor(description: String): this(null, description, null)
}
