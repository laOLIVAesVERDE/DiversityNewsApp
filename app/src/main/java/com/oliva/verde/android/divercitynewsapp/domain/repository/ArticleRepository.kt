package com.oliva.verde.android.divercitynewsapp.domain.repository

import com.oliva.verde.android.divercitynewsapp.data.remote.dto.ArticleDto

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

    suspend fun getArticles(searchWord: String): ArticleDto


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