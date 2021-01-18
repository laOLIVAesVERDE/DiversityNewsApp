package com.oliva.verde.android.divercitynewsapp.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class StockArticle (
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var url : String = "url",
    var urlToImage : String? = "urlToImage",
    var publishedAt : String = "publishedAt",
    var title : String = "title",
    var isReadFlag : Boolean = false
)