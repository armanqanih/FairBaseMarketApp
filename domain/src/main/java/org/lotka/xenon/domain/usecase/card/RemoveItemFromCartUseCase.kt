package org.lotka.xenon.domain.usecase.card

import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class RemoveItemFromCartUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
    suspend operator fun invoke(itemId: String) {
        itemsRepository.removeItemFromCart(itemId)
    }
}