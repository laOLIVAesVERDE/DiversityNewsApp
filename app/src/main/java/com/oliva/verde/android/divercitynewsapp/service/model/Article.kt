package com.oliva.verde.android.divercitynewsapp.service.model

data class Article(
    var url : String = "url",
    var urlToImage : String? = "urlToImage",
    var publishedAt : String = "publishedAt",
    var title : String = "title"
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