package org.lotka.xenon.domain.repository

import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.WishListModel

interface WishListRepository {
    suspend fun saveItemToWishList(item: WishListModel)
    fun getItemsInWishList(): Flow<List<WishListModel>>
    suspend fun removeItemFromWishList(itemId: String)

}