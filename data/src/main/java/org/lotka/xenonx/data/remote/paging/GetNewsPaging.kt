package org.lotka.xenonx.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import org.lotka.xenonx.data.api.NewsApi
import org.lotka.xenonx.data.remote.dto.ArticleDto

class GetNewsPaging(
    private val newsApi: NewsApi,
    private val sources: String
) : PagingSource<Int, ArticleDto>() {


    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        return try {
            val currentPage = params.key ?: 1
            delay(3000L)
            val response = newsApi.getNews(sources = sources, page = currentPage)
            val nextKey =   currentPage + 1
            val prevKey = if (currentPage == 1) null else currentPage - 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}