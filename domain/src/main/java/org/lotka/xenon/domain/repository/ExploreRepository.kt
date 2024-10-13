package org.lotka.xenon.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.CardModel
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Item
import org.lotka.xenon.domain.model.WishListModel
import org.lotka.xenon.domain.util.Resource

interface ExploreRepository {

    fun getCategories(): Flow<Resource<List<Category>>>
    fun getHomeItem(): Flow<Resource<List<Item>>>
    fun getDetailItem(itemId: String): Flow<Resource<Item>>
    fun getItemsByCategory(categoryId: String): Flow<PagingData<Item>>






//    for search
fun searchItems(query: String): Flow<Resource<List<Item>>>


}