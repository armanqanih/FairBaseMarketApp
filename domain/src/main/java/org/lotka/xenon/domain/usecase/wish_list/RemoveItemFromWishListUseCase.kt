package org.lotka.xenon.domain.usecase.wish_list

import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class RemoveItemFromWishListUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
    suspend operator fun invoke(itemId: String) {
        itemsRepository.removeItemFromWishList(itemId)
    }
}