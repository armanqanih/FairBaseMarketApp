package org.lotka.xenon.domain.usecase.card

import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class SaveItemToCartUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
    suspend operator fun invoke(item: CardModel) {
        itemsRepository.saveItemToCart(item)
    }
}
