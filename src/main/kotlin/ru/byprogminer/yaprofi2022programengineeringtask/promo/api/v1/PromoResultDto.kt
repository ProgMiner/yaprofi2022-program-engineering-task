package ru.byprogminer.yaprofi2022programengineeringtask.promo.api.v1


data class PromoResultDto(
    val winner: WinnerDto,
    val prize: PrizeDto,
) {

    data class WinnerDto(
        val id: Long,
        val name: String,
    )

    data class PrizeDto(
        val id: Long,
        val description: String,
    )
}
