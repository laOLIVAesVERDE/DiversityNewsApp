package com.oliva.verde.android.divercitynewsapp

import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository {
    @Inject
    lateinit var apiService : ApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    companion object Factory {
        val instance : Repository
        @Synchronized get() {
            return Repository()
        }
    }

    fun getNewsArticles(apiKey: String, searchWord: String): Observable<List<Article>> {
        return apiService.getNews(apiKey, searchWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                return@map it.articles
            }
    }
}