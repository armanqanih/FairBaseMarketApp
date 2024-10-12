package org.lotka.xenon.domain.usecase.wish_list

import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class SaveItemToWishListUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
    suspend operator fun invoke(item: WishListModel) {
        itemsRepository.saveItemToWishList(item)
    }
}
