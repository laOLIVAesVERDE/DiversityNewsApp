package com.oliva.verde.android.divercitynewsapp.service.repository

import android.util.Log
import com.oliva.verde.android.divercitynewsapp.MyApplication
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import com.oliva.verde.android.divercitynewsapp.service.repository.api.ApiService
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import retrofit2.Response
import javax.inject.Inject

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


    suspend fun getStockedArticles() : MutableList<Article.StockArticle> {
        Log.d(LOGTAG, "getStockedArticles")
        return stockArticleDao.findAll()
    }

    suspend fun insertArticle(targetArticle : Article.StockArticle) {
        Log.d(LOGTAG, "insertArticle")
        stockArticleDao.add(targetArticle)
    }

    suspend fun deleteStockArticle(targetArticle: Article.StockArticle) {
        Log.d(LOGTAG, "deleteArticle")
        stockArticleDao.delete(targetArticle)
    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        StockArticleDao.delete(targetArticle)
    }

     */
}