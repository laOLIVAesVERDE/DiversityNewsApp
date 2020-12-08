package com.oliva.verde.android.divercitynewsapp

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException

class Repository(val apiService: ApiService) {

    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<List<Article>> {
        return apiService.getNews(apiKey, searchWord)
    }
}