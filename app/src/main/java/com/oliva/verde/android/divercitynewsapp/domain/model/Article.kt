package com.oliva.verde.android.divercitynewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class Article {
    data class ResponseArticle(
        val url: String = "url",
        val urlToImage: String? = "urlToImage",
        val publishedAt: String = "publishedAt",
        val title: String = "title"
    ) : Article()

    @Entity
    data class StockArticle (
        @PrimaryKey(autoGenerate = true)
        val id : Long,
        val url : String = "url",
        val urlToImage : String? = "urlToImage",
        val publishedAt : String = "publishedAt",
        val title : String = "title",
        val isReadFlag : Boolean = false
    ) : Article()
}

data class ResponseData(val articles : MutableList<Article.ResponseArticle>)