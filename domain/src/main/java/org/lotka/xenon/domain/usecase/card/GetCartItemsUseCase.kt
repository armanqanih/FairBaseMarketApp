package org.lotka.xenon.domain.usecase.card

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.repository.CardRepository
import org.lotka.xenon.domain.repository.ExploreRepository
import javax.inject.Inject

class GetItemsInCartUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
      operator fun invoke(): Flow<List<CardModel>> {
        return cardRepository.getItemsInCart()
    }
}