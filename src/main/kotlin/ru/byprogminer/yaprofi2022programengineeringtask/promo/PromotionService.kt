package ru.byprogminer.yaprofi2022programengineeringtask.promo

import org.springframework.stereotype.Service
import java.security.SecureRandom
import javax.transaction.Transactional


@Service
@Transactional
class PromotionService(
    private val repository: PromotionRepository,
    private val participantRepository: ParticipantRepository,
    private val prizeRepository: PrizeRepository,
) {

    fun getAllPromo(): List<Promotion> = repository.findAll()

    fun getPromo(id: Long): Promotion? = repository.findById(id).orElse(null)
        ?.apply { prizes; participants }

    fun createPromo(promo: Promotion): Long = repository.save(promo).id!!

    fun editPromo(id: Long, changes: Promotion.() -> Unit): Promotion? =
        repository.findById(id).orElse(null)?.also(changes)

    fun deletePromo(id: Long): Boolean {
        if (!repository.existsById(id)) {
            return false
        }

        repository.deleteById(id)
        return true
    }

    fun addParticipant(id: Long, participant: Participant): Long? {
        val promo = repository.findById(id).orElse(null) ?: return null

        participant.promotion = promo
        return participantRepository.save(participant).id
    }

    fun deleteParticipant(promoId: Long, participantId: Long): Boolean {
        val participant = participantRepository.findById(participantId).orElse(null) ?: return false

        if (participant.promotion?.id != promoId) {
            return false
        }

        participantRepository.delete(participant)
        return true
    }

    fun addPrize(id: Long, prize: Prize): Long? {
        val promo = repository.findById(id).orElse(null) ?: return null

        prize.promotion = promo
        return prizeRepository.save(prize).id
    }

    fun deletePrize(promoId: Long, prizeId: Long): Boolean {
        val prize = prizeRepository.findById(prizeId).orElse(null) ?: return false

        if (prize.promotion?.id != promoId) {
            return false
        }

        prizeRepository.delete(prize)
        return true
    }

    fun raffle(id: Long): PromotionResult {
        val promo = repository.findById(id).orElse(null) ?: return PromotionResult.NotFound

        if (promo.participants.size != promo.prizes.size) {
            return PromotionResult.Conflict
        }

        val random = SecureRandom()
        val participants = promo.participants.toList()
        val prizes = promo.prizes.toList().shuffled(random)

        return participants.zip(prizes).toMap().let { PromotionResult.Success(it) }
    }
}
