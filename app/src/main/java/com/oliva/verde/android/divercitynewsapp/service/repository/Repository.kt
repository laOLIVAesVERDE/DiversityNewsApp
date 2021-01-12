package com.oliva.verde.android.divercitynewsapp.service.repository

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

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> {
        return apiService.getNews(apiKey, searchWord)
    }

    fun getStockedArticles() : MutableList<Article> {
        return StockArticleDao.selectAll()
    }

    fun insertArticle(targetArticle : Article) {
        StockArticleDao.insert(targetArticle)
    }

    fun deleteTargetArticle(targetArticle: Article) {
        StockArticleDao.delete(targetArticle)
    }
}