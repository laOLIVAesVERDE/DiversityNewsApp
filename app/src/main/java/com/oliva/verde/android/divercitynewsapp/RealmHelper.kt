package com.oliva.verde.android.divercitynewsapp

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
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
        Log.d("NewsApp", title)
        mRealm.executeTransaction {
            var article = mRealm.createObject(Article::class.java, UUID.randomUUID().toString())
            article.url = url
            article.urlToImage = urlToImage
            article.publishedAt = publishedAt
            article.title = title
            mRealm.copyToRealm(article)
            Log.d("NewsApp", article.id)
        }
    }

    fun read() : RealmResults<Article> {
        return mRealm.where(Article::class.java).findAll()
    }

    fun delete(id : String) {
        Log.d("NewsApp", "Realm Delete")
        mRealm.executeTransaction {
            var article = mRealm.where(Article::class.java).equalTo("id", id).findFirst()
            article.deleteFromRealm()
        }
    }

    fun search(query : String): MutableList<Article> {
        return mRealm.where(Article::class.java).contains("title", query).findAll()
    }

    fun deleteAll() {
        mRealm.executeTransaction {
            mRealm.where(Article::class.java).findAll().deleteAllFromRealm()
        }
    }
}