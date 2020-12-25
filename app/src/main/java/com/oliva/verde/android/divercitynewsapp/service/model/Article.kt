package com.oliva.verde.android.divercitynewsapp.service.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

data class Article(
    @PrimaryKey open var id : String = UUID.randomUUID().toString(),
    val url : String = "",
    val urlToImage : String? = "",
    val publishedAt : String = "",
    val title : String = ""
) : RealmObject()
    /**
    //@PrimaryKey open var id : String = UUID.randomUUID().toString(),
    open var url: String = "url",
    open var urlToImage: String = "urlToImage",
    open var publishedAt: String = "publishedAt",
    open var title: String = "title",
    open var isReadFlag : Boolean = false)
    // : RealmObject() {}
    */