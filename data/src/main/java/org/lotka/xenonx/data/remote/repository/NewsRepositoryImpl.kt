package org.lotka.xenonx.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lotka.xenonx.data.api.NewsApi
import org.lotka.xenonx.data.remote.paging.GetNewsPaging
import org.lotka.xenonx.data.remote.paging.SearchNewsPagingSource
import org.lotka.xenonx.domain.model.Article
import org.lotka.xenonx.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
   private val newsApi: NewsApi,
):NewsRepository {
    override suspend fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return flow {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false),
                pagingSourceFactory = { GetNewsPaging(newsApi, sources.joinToString(separator = ",")) }
            )

        }
    }

    override suspend fun searchNews(
        searchQuery: String,
        sources: String,
    ): Flow<PagingData<Article>> {
        return flow {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false),
                pagingSourceFactory = { SearchNewsPagingSource(newsApi,sources,searchQuery) }
            )

        }
    }

}