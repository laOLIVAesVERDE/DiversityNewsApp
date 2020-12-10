package com.oliva.verde.android.divercitynewsapp

import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException

class Repository {
    private lateinit var apiService : ApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    companion object Factory {
        val instance : Repository
        @Synchronized get() {
            return Repository()
        }
    }


    suspend fun getNewsArticles(apiKey: String, searchWord: String): Response<ResponseData> =
        apiService.getNews(apiKey, searchWord).execute()

}