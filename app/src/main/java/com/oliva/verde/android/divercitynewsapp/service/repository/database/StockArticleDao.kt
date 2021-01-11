package com.oliva.verde.android.divercitynewsapp.service.repository.database

import com.oliva.verde.android.divercitynewsapp.service.model.Article
import io.realm.Realm
import io.realm.RealmResults
import java.util.*


class StockArticleDao(val mRealm : Realm) {


    suspend fun insert(targetArticle: Article) {
        mRealm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                val article = realm.createObject(Article::class.java, UUID.randomUUID().toString())
                article.apply {
                    url = targetArticle.url
                    urlToImage = targetArticle.urlToImage
                    publishedAt = targetArticle.publishedAt
                    title = targetArticle.title
                    isReadFlag = false
                    realm.insert(article)
                }
            }
        })
    }

    fun selectAll() : MutableList<Article> {
        return mRealm.where(Article::class.java).findAll()
    }

    fun delete(targetArticle: Article) {
        mRealm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                val article = realm.where(Article::class.java).equalTo("id", targetArticle.id).findFirst()
                article?.deleteFromRealm()
            }
        })
    }

    fun search(query : String): MutableList<Article> {
        return mRealm.where(Article::class.java).contains("title", query).findAll()
    }

    // 未読記事から記事を検索
    fun searchFromIsNotRead(query: String) : MutableList<Article> {
        return mRealm.where(Article::class.java)
            .equalTo("isReadFlag", false)
            .contains("title", query)
            .findAll()
    }

    fun updateFlag(id : String) {
        mRealm.executeTransaction {
            val article = mRealm.where(Article::class.java).equalTo("id", id).findFirst()
            if (article != null) {
                article.isReadFlag = true
            }
        }
    }

    fun readIsNotRead() :RealmResults<Article> {
        return mRealm.where(Article::class.java).equalTo("isReadFlag", false).findAll()
    }

}