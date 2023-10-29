package com.example.mandiri_news
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface beritaInterface {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): Response<NewsResponse>
}

data class NewsResponse(val articles: List<berita>)