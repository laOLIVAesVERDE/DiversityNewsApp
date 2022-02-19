package com.oliva.verde.android.divercitynewsapp.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

data class ArticleDto(
    @SerializedName("articles")
    val articles: List<ArticleData>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)

fun ArticleDto.toArticleList(): List<Article.ResponseArticle> {
    return articles.map { article ->
        Article.ResponseArticle(
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            title = article.title
        )
    }
}