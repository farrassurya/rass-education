package com.example.rass_education.Home.tugas_p11

import android.util.Log
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
                if (response.isSuccessful) {
                    val articles = response.body()?.results.orEmpty()
                    onResult(Result.success(articles))
                } else {
                    Log.e("NewsRepository", "Error Response: ${response.code()} - ${response.message()}")
                    onResult(Result.failure(IllegalStateException("Response berita tidak valid")))
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("NewsRepository", "Network Failure: ${t.message}", t)
                onResult(Result.failure(t))
            }
        })
    }
}
