package com.oliva.verde.android.divercitynewsapp.service.repository.database

import android.widget.Toast
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import java.util.*


class StockArticleDao(val realm : Realm) {


    fun insert(targetArticle: Article) {
        realm.executeTransaction {
            val article = realm.createObject(Article::class.java, UUID.randomUUID().toString())
            article.url = targetArticle.url
            article.urlToImage = targetArticle.urlToImage
            article.publishedAt = targetArticle.publishedAt
            article.title = targetArticle.title
            article.isReadFlag = false
            realm.insert(article)
        }
    }

    fun select() : RealmResults<Article> {
        return realm.where(Article::class.java).findAll()
    }

    fun delete(targetArticle: Article) {
        realm.executeTransaction {
            val article = realm.where(Article::class.java).equalTo("id", targetArticle.id).findFirst()
            if (article != null) {
                article.deleteFromRealm()
            }
        }
    }

    fun search(query : String): MutableList<Article> {
        return realm.where(Article::class.java).contains("title", query).findAll()
    }

    // 未読記事から記事を検索
    fun searchFromIsNotRead(query: String) : MutableList<Article> {
        return realm.where(Article::class.java)
            .equalTo("isReadFlag", false)
            .contains("title", query)
            .findAll()
    }

    fun updateFlag(id : String) {
        realm.executeTransaction {
            val article = realm.where(Article::class.java).equalTo("id", id).findFirst()
            if (article != null) {
                article.isReadFlag = true
            }
        }
    }

    fun readIsNotRead() :RealmResults<Article> {
        return realm.where(Article::class.java).equalTo("isReadFlag", false).findAll()
    }

}