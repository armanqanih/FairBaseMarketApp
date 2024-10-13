package org.lotka.xenon.domain.usecase.wish_list

import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.repository.ExploreRepository
import org.lotka.xenon.domain.repository.WishListRepository
import javax.inject.Inject

class SaveItemToWishListUseCase @Inject constructor(
    private val wishListRepository: WishListRepository
) {
    suspend operator fun invoke(item: WishListModel) {
        wishListRepository.saveItemToWishList(item)
    }
}
