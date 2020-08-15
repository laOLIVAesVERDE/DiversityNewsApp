package com.oliva.verde.android.divercitynewsapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Article(
    @PrimaryKey open var id : String = UUID.randomUUID().toString(),
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var title: String) : RealmObject() {}