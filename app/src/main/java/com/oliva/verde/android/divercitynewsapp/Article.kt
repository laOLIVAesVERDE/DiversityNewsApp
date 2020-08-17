package com.oliva.verde.android.divercitynewsapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Article(
    @PrimaryKey open var id : String = UUID.randomUUID().toString(),
    open var url: String = "url",
    open var urlToImage: String = "urlToImage",
    open var publishedAt: String = "publishedAt",
    open var title: String = "title") : RealmObject() {}