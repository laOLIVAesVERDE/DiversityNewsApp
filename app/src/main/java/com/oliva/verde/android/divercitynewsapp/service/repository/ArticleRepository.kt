package com.oliva.verde.android.divercitynewsapp.service.repository

import android.util.Log
import com.oliva.verde.android.divercitynewsapp.MyApplication
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import com.oliva.verde.android.divercitynewsapp.data.remote.ArticleApiService
import com.oliva.verde.android.divercitynewsapp.data.local.StockArticleDao
import com.oliva.verde.android.divercitynewsapp.data.remote.dto.ArticleDto
import retrofit2.Response
import javax.inject.Inject

interface ArticleRepository {


//    @Inject
//    lateinit var articleApiService : ArticleApiService
//
//    @Inject
//    lateinit var stockArticleDao: StockArticleDao
//
//    init {
//        MyApplication.appComponent.inject(this)
//    }

    suspend fun getArticles(searchWord: String): List<ArticleDto>


//    suspend fun getStockedArticles() : MutableList<Article.StockArticle> {
//        Log.d(LOGTAG, "getStockedArticles")
//        return stockArticleDao.findAll()
//    }
//
//    suspend fun insertArticle(targetArticle : Article.StockArticle) {
//        Log.d(LOGTAG, "insertArticle")
//        stockArticleDao.add(targetArticle)
//    }
//
//    suspend fun deleteStockArticle(targetArticle: Article.StockArticle) {
//        Log.d(LOGTAG, "deleteArticle")
//        stockArticleDao.delete(targetArticle)
//    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        StockArticleDao.delete(targetArticle)
    }

     */
}