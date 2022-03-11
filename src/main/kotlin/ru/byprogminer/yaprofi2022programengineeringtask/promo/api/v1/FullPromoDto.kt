package ru.byprogminer.yaprofi2022programengineeringtask.promo.api.v1

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class FullPromoDto(
    val id: Long,
    val name: String,
    val description: String?,
    val prizes: List<PrizeDto>,
    val participants: List<ParticipantDto>,
) {

    data class PrizeDto(
        val id: Long,
        val description: String,
    )

    data class ParticipantDto(
        val id: Long,
        val name: String,
    )
}
