package com.oliva.verde.android.divercitynewsapp

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class Repository(val apiService: ApiService) {

    fun getNewsArticles(apiKey : String, searchWord : String) : Single<List<Article>> {
        return apiService.getNews(apiKey, searchWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val body = it.body()
                    ?: throw IOException("failed to fetch")
                return@map body
            }
    }
}