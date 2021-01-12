package com.oliva.verde.android.divercitynewsapp.service.repository.database

import com.oliva.verde.android.divercitynewsapp.service.model.Article
import io.realm.Realm
import io.realm.RealmResults
import java.util.*


object StockArticleDao {
    fun insert(targetArticle: Article) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync(object : Realm.Transaction {
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
        val realm = Realm.getDefaultInstance()
        val articles = realm.where(Article::class.java).findAll()

        return articles
    }

    fun delete(targetArticle: Article) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                val article = realm.where(Article::class.java).equalTo("id", targetArticle.id).findFirst()
                article?.deleteFromRealm()
            }
        })
    }

    fun search(query : String): MutableList<Article> {
        val realm = Realm.getDefaultInstance()
        val searchedArticles = realm.where(Article::class.java).contains("title", query).findAll()

        return searchedArticles
    }

    // 未読記事から記事を検索
    fun searchFromIsNotRead(query: String) : MutableList<Article> {
        val realm = Realm.getDefaultInstance()
        val searchedArticles =  realm.where(Article::class.java)
            .equalTo("isReadFlag", false)
            .contains("title", query)
            .findAll()
        return searchedArticles
    }

    fun updateFlag(id : String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val article = realm.where(Article::class.java).equalTo("id", id).findFirst()
            if (article != null) {
                article.isReadFlag = true
            }
        }
    }

    fun searchNotReadArticles() :RealmResults<Article> {
        val realm = Realm.getDefaultInstance()
        val searchedArticles = realm.where(Article::class.java).equalTo("isReadFlag", false).findAll()

        return searchedArticles
    }

}