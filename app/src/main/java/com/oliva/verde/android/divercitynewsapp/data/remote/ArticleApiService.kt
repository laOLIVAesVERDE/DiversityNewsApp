package com.oliva.verde.android.divercitynewsapp.data.remote

import com.oliva.verde.android.divercitynewsapp.BuildConfig
import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("v2/everything/")
    suspend fun getArticles(@Query("apiKey") apiKey: String = BuildConfig.API_KEY,
                            @Query("q") searchWord : String) : Response<ResponseData>
}