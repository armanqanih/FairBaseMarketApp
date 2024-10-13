package org.lotka.xenon.domain.usecase.wish_list

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.repository.ExploreRepository
import org.lotka.xenon.domain.repository.WishListRepository
import javax.inject.Inject

class GetItemsInWishListUseCase @Inject constructor(
    private val wishListRepository: WishListRepository
) {
      operator fun invoke(): Flow<List<WishListModel>> {
        return wishListRepository.getItemsInWishList()
    }
}