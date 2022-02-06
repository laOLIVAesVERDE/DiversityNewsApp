package com.oliva.verde.android.divercitynewsapp.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockArticleDto (
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val url : String = "url",
    val urlToImage : String? = "urlToImage",
    val publishedAt : String = "publishedAt",
    val title : String = "title",
    val isReadFlag : Boolean = false
)
