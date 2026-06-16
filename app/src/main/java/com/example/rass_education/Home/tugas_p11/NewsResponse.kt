package com.example.rass_education.Home.tugas_p11

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("results") val results: List<NewsArticle> = emptyList()
)

