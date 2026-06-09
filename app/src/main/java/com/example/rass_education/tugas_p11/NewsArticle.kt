package com.example.rass_education.tugas_p11

import com.google.gson.annotations.SerializedName

data class NewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("news_site") val newsSite: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("url") val url: String
)

