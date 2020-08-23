package com.oliva.verde.android.divercitynewsapp

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import java.util.*

class RealmHelper {
    var mRealm = Realm.getInstance(
         RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .build()
    )

    fun create(url : String,
               urlToImage : String,
               publishedAt : String,
               title : String) {
        mRealm.executeTransaction {
            val article = mRealm.createObject(Article::class.java, UUID.randomUUID().toString())
            article.url = url
            article.urlToImage = urlToImage
            article.publishedAt = publishedAt
            article.title = title
            mRealm.copyToRealm(article)
        }
    }

    fun read() : RealmResults<Article> {
        return mRealm.where(Article::class.java).findAll()
    }

    fun delete(id : String) {
        mRealm.executeTransaction {
            val article = mRealm.where(Article::class.java).equalTo("id", id).findFirst()
            article.deleteFromRealm()
        }
    }

    fun search(query : String): MutableList<Article> {
        return mRealm.where(Article::class.java).contains("title", query).findAll()
    }

}