package com.oliva.verde.android.divercitynewsapp.service.repository

import android.app.Application
import android.util.Log
import com.oliva.verde.android.divercitynewsapp.MyApplication
import com.oliva.verde.android.divercitynewsapp.injection.ApiModule
import com.oliva.verde.android.divercitynewsapp.injection.AppComponentModule
import com.oliva.verde.android.divercitynewsapp.injection.DaggerAppComponent
import com.oliva.verde.android.divercitynewsapp.injection.DatabaseModule
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import com.oliva.verde.android.divercitynewsapp.service.model.StockArticle
import com.oliva.verde.android.divercitynewsapp.service.repository.api.ApiService
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import io.realm.Realm
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class Repository {

    companion object {
        val LOGTAG = "Repository"
        val instance : Repository
            @Synchronized get() {
                return Repository()
            }
    }

    @Inject
    lateinit var apiService : ApiService

    @Inject
    lateinit var stockArticleDao: StockArticleDao

    init {
        MyApplication.appComponent.inject(this)
    }

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> {
        return apiService.getNews(apiKey, searchWord)
    }


    suspend fun getStockedArticles() : MutableList<StockArticle> {
        Log.d(LOGTAG, "getStockedArticles")
        return stockArticleDao.findAll()
    }

    suspend fun insertArticle(targetArticle : StockArticle) {
        Log.d(LOGTAG, "insertArticle")
        stockArticleDao.add(targetArticle)
    }

    suspend fun deleteStockArticle(targetArticle: StockArticle) {
        Log.d(LOGTAG, "deleteArticle")
        stockArticleDao.delete(targetArticle)
    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        StockArticleDao.delete(targetArticle)
    }

     */
}