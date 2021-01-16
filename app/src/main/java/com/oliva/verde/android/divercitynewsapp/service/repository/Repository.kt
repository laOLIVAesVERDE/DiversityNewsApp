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
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> {
        return apiService.getNews(apiKey, searchWord)
    }


    suspend fun getStockedArticles() : MutableList<Article> {
        Log.d(LOGTAG, "getStockedArticles")
        return stockArticleDao.findAll()
    }

    suspend fun insertArticle(targetArticle : Article) {
        Log.d(LOGTAG, "insertArticle")
        stockArticleDao.add(targetArticle)
    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        StockArticleDao.delete(targetArticle)
    }

     */
}