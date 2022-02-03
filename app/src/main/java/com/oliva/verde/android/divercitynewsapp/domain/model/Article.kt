package com.oliva.verde.android.divercitynewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class Article {
    data class ResponseArticle(
        var url: String = "url",
        var urlToImage: String? = "urlToImage",
        var publishedAt: String = "publishedAt",
        var title: String = "title"
    ) : Article()

    @Entity
    data class StockArticle (
        @PrimaryKey(autoGenerate = true)
        val id : Long,
        var url : String = "url",
        var urlToImage : String? = "urlToImage",
        var publishedAt : String = "publishedAt",
        var title : String = "title",
        var isReadFlag : Boolean = false
    ) : Article()
}

data class ResponseData(val articles : MutableList<Article.ResponseArticle>)