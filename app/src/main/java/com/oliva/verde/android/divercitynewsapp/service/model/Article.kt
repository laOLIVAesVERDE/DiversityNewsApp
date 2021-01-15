package com.oliva.verde.android.divercitynewsapp.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var url : String = "url",
    var urlToImage : String? = "urlToImage",
    var publishedAt : String = "publishedAt",
    var title : String = "title",
    var isReadFlag : Boolean = false
)

data class ResponseData(val articles : MutableList<Article>)
    /**
    //@PrimaryKey open var id : String = UUID.randomUUID().toString(),
    open var url: String = "url",
    open var urlToImage: String = "urlToImage",
    open var publishedAt: String = "publishedAt",
    open var title: String = "title",
    open var isReadFlag : Boolean = false)
    // : RealmObject() {}
    */