package ru.byprogminer.yaprofi2022programengineeringtask.promo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface PrizeRepository: JpaRepository<Prize, Long>
