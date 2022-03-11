package ru.byprogminer.yaprofi2022programengineeringtask.promo.api.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.byprogminer.yaprofi2022programengineeringtask.promo.*
import java.util.*


@RestController
@RequestMapping("/promo")
class PromotionController(
    private val service: PromotionService,
) {

    @PostMapping
    fun createPromo(@RequestBody promo: CreatePromoDto): Long =
        service.createPromo(Promotion(promo.name, promo.description))

    @GetMapping
    fun listPromo(): List<ListPromoDto> =
        service.getAllPromo().map { e ->
            ListPromoDto(
                e.id!!,
                e.name!!,
                e.description,
            )
        }

    @GetMapping("/{id:\\d+}")
    fun getPromo(@PathVariable id: Long): ResponseEntity<FullPromoDto> =
        service.getPromo(id).let { Optional.ofNullable(it) }
            .map { promo ->
                FullPromoDto(
                    promo.id!!,
                    promo.name!!,
                    promo.description,
                    promo.prizes.map { e -> FullPromoDto.PrizeDto(e.id!!, e.description!!) },
                    promo.participants.map { e -> FullPromoDto.ParticipantDto(e.id!!, e.name!!) },
                )
            }.let { ResponseEntity.of(it) }

    @PutMapping("/{id:\\d+}")
    fun editPromo(@PathVariable id: Long, @RequestBody changes: EditPromoDto): ResponseEntity<ListPromoDto> =
        service.editPromo(id) {
            name = changes.name
            description = changes.description
        }.let { Optional.ofNullable(it) }
            .map { p -> ListPromoDto(p.id!!, p.name!!, p.description) }
            .let { ResponseEntity.of(it) }

    @DeleteMapping("/{id:\\d+}")
    fun deletePromo(@PathVariable id: Long): ResponseEntity<Any> =
        if (service.deletePromo(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }

    @PostMapping("/{id:\\d+}/participant")
    fun addParticipant(@PathVariable id: Long, @RequestBody participant: AddParticipantDto): ResponseEntity<Long> =
        service.addParticipant(id, Participant(participant.name))
            .let { Optional.ofNullable(it) }
            .let { ResponseEntity.of(it) }

    @DeleteMapping("/{promoId:\\d+}/participant/{participantId:\\d+}")
    fun deleteParticipant(@PathVariable promoId: Long, @PathVariable participantId: Long): ResponseEntity<Any> =
        if (service.deleteParticipant(promoId, participantId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }

    @PostMapping("/{id:\\d+}/prize")
    fun addPrize(@PathVariable id: Long, @RequestBody prize: AddPrizeDto): ResponseEntity<Long> =
        service.addPrize(id, Prize(prize.description))
            .let { Optional.ofNullable(it) }
            .let { ResponseEntity.of(it) }

    @DeleteMapping("/{promoId:\\d+}/prize/{prizeId:\\d+}")
    fun deletePrize(@PathVariable promoId: Long, @PathVariable prizeId: Long): ResponseEntity<Any> =
        if (service.deletePrize(promoId, prizeId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }

    @PostMapping("/{id:\\d+}/raffle")
    fun raffle(@PathVariable id: Long): ResponseEntity<List<PromoResultDto>> =
        when (val result = service.raffle(id)) {
            PromotionResult.NotFound -> ResponseEntity.notFound().build()
            PromotionResult.Conflict -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is PromotionResult.Success -> result.prizes.map { e -> PromoResultDto(
                PromoResultDto.WinnerDto(e.key.id!!, e.key.name!!),
                PromoResultDto.PrizeDto(e.value.id!!, e.value.description!!),
            ) }.let { ResponseEntity.ok(it) }
        }
}
