package org.lotka.xenon.domain.usecase.wish_list

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.repository.HomeRepository
import javax.inject.Inject

class GetItemsInWishListUseCase @Inject constructor(
    private val itemsRepository: HomeRepository
) {
      operator fun invoke(): Flow<List<WishListModel>> {
        return itemsRepository.getItemsInWishList()
    }
}