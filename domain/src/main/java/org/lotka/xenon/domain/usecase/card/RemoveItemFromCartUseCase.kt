package org.lotka.xenon.domain.usecase.card

import org.lotka.xenon.domain.repository.CardRepository
import org.lotka.xenon.domain.repository.ExploreRepository
import javax.inject.Inject

class RemoveItemFromCartUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(itemId: String) {
        cardRepository.removeItemFromCart(itemId)
    }
}