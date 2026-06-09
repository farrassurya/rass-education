package com.example.rass_education.tugas_p11

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(
    private val apiService: NewsApiService = NewsApiClient.service
) {

    fun fetchLatestNews(
        limit: Int = 5,
        onResult: (Result<List<NewsArticle>>) -> Unit
    ) {
        // P11: request API publik dijalankan tanpa coroutine agar alurnya tetap mudah diikuti.
        apiService.getLatestNews(limit).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val articles = response.body()?.results.orEmpty()
                if (response.isSuccessful) {
                    onResult(Result.success(articles))
                } else {
                    onResult(Result.failure(IllegalStateException("Response berita tidak valid")))
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }
}


