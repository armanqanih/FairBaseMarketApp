package org.lotka.xenon.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenon.domain.model.Category
import org.lotka.xenon.domain.model.Items
import org.lotka.xenon.domain.util.Resource

interface HomeRepository {

    fun getCategories(): Flow<Resource<List<Category>>>
    fun getHomeItem(): Flow<Resource<List<Items>>>
    fun getDetailItem(itemId: String): Flow<Resource<Items>>
     fun getItemsByCategory(categoryId: String): Flow<PagingData<Items>>






}