package com.oliva.verde.android.divercitynewsapp.service.model

data class Article(
    val url : String = "",
    val urlToImage : String? = "",
    val publishedAt : String = "",
    val title : String = ""
)
    /**
    //@PrimaryKey open var id : String = UUID.randomUUID().toString(),
    open var url: String = "url",
    open var urlToImage: String = "urlToImage",
    open var publishedAt: String = "publishedAt",
    open var title: String = "title",
    open var isReadFlag : Boolean = false)
    // : RealmObject() {}
    */