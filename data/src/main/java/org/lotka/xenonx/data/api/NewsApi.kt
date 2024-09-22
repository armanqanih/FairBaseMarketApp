package org.lotka.xenonx.data.api




import org.lotka.xenonx.data.remote.dto.ArticleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ):List<ArticleDto>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ):List<ArticleDto>
}

const val API_KEY =  "c22f3da97b2942a9bcd0ed185f3ee378"