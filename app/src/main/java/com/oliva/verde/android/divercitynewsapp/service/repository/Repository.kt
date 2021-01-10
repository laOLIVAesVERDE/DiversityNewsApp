package com.oliva.verde.android.divercitynewsapp.service.repository

import android.util.Log
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import com.oliva.verde.android.divercitynewsapp.service.repository.api.ApiService
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import io.realm.Realm
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class Repository {
    val LOGTAG = "Repository"
    private val realm : Realm = Realm.getDefaultInstance()
    val stockArticleDao : StockArticleDao = StockArticleDao(realm)

    @Singleton
    var stockArticleList : RealmResults<Article> = stockArticleDao.select()

    @Inject
    lateinit var apiService : ApiService

    init {
        DaggerApiComponent.create().inject(this)
        Log.d(LOGTAG, "getNewsArticles called")

    }

    companion object Factory {
        val instance : Repository
        @Synchronized get() {
            return Repository()
        }
    }

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> =
        apiService.getNews(apiKey, searchWord)


}