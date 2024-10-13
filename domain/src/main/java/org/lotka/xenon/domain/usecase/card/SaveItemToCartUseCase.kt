package org.lotka.xenon.domain.usecase.card

import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.repository.CardRepository
import org.lotka.xenon.domain.repository.ExploreRepository
import javax.inject.Inject

class SaveItemToCartUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(item: CardModel) {
        cardRepository.saveItemToCart(item)
    }
}
