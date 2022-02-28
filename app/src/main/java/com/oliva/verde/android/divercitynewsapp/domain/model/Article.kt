package com.oliva.verde.android.divercitynewsapp.domain.model

import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto

sealed class Article {
    data class ResponseArticle(
        val url: String = "url",
        val urlToImage: String? = "urlToImage",
        val publishedAt: String = "publishedAt",
        val title: String = "title"
    ) : Article()

    data class StockArticle (
        val id : Long,
        val url : String = "url",
        val urlToImage : String? = "urlToImage",
        val publishedAt : String = "publishedAt",
        val title : String = "title",
        val isReadFlag : Boolean = false
    ) : Article() {
        fun StockArticle.toStockArticleDto() : StockArticleDto {
            return StockArticleDto(id, url, urlToImage, publishedAt, title, isReadFlag)
        }
    }
}