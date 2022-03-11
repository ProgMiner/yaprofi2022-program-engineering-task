package ru.byprogminer.yaprofi2022programengineeringtask.promo.api.v1

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListPromoDto(
    val id: Long,
    val name: String,
    val description: String?,
)
