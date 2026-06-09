package com.example.rass_education.tugas_p11

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("articles/")
    fun getLatestNews(
        @Query("limit") limit: Int = 5
    ): Call<NewsResponse>
}

object NewsApiClient {
    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"

    val service: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}


