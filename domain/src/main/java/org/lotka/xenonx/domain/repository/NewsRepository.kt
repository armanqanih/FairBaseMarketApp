package org.lotka.xenonx.domain.repository


import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.lotka.xenonx.domain.model.Article


interface NewsRepository {

    suspend fun getNews(sources: List<String>
    ): Flow<PagingData<Article>>


    suspend fun searchNews(
        searchQuery: String,
        sources: String,
        ):Flow<PagingData<Article>>


}