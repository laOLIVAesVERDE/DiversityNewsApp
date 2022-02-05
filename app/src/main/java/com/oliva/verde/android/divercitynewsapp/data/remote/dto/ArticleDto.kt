package com.oliva.verde.android.divercitynewsapp.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

data class ArticleDto(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)

fun ArticleDto.toArticle() : Article.ResponseArticle {
    return Article.ResponseArticle(url, urlToImage, publishedAt, title)
}