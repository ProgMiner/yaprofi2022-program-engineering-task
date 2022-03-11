package ru.byprogminer.yaprofi2022programengineeringtask.promo.api.v1

import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreatePromoDto(
    val name: String,
    val description: String?,
)
