package org.lotka.xenon.domain.repository

import androidx.paging.PagingData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.util.Resource

interface HomeRepository {

    fun getCategories(): Flow<Resource<List<Category>>>
    fun getHomeItem(): Flow<Resource<List<Item>>>
    fun getDetailItem(itemId: String): Flow<Resource<Item>>
    fun getItemsByCategory(categoryId: String): Flow<PagingData<Item>>

//for add and remove data to card screen

    suspend fun saveItemToCart(item: CardModel)
    fun getItemsInCart(): Flow<List<CardModel>>
    suspend fun removeItemFromCart(itemId: String)

//    for WishList

    suspend fun saveItemToWishList(item: WishListModel)
    fun getItemsInWishList(): Flow<List<WishListModel>>
    suspend fun removeItemFromWishList(itemId: String)
}