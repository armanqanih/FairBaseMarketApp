package org.lotka.xenon.domain.usecase.card

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class GetItemsInCartUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
      operator fun invoke(): Flow<List<CardModel>> {
        return itemsRepository.getItemsInCart()
    }
}