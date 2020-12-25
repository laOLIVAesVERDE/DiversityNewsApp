package com.oliva.verde.android.divercitynewsapp.service.repository

import android.util.Log
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import com.oliva.verde.android.divercitynewsapp.service.repository.api.ApiService
import com.oliva.verde.android.divercitynewsapp.service.repository.api.ResponseData
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

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> =
        apiService.getNews(apiKey, searchWord)


}