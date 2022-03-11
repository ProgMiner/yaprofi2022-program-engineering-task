package ru.byprogminer.yaprofi2022programengineeringtask.promo

sealed class PromotionResult {

    object NotFound: PromotionResult()
    object Conflict: PromotionResult()

    data class Success(val prizes: Map<Participant, Prize>): PromotionResult()
}
