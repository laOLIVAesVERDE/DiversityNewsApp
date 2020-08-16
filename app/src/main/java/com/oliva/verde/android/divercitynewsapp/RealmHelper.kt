package com.oliva.verde.android.divercitynewsapp

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RealmHelper {
    var mRealm = Realm.getDefaultInstance()
    fun create(url : String,
               urlToImage : String,
               publishedAt : String,
               title : String) {
        Log.d("NewsApp", title)
        mRealm.executeTransaction {
            var article = mRealm.createObject(Article::class.java, UUID.randomUUID().toString())
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
            var article = mRealm.where(Article::class.java).equalTo("id", id).findAll()
            article.deleteFromRealm(0)
        }
    }
}