package com.oliva.verde.android.divercitynewsapp

import android.util.Log
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class Repository {
    val LOGTAG = "Repository"

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

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<List<Article>> =
        apiService.getNews(apiKey, searchWord)
}