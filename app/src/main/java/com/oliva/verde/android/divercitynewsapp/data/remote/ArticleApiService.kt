package com.oliva.verde.android.divercitynewsapp.data.remote

import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("v2/everything/")
    suspend fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Response<ResponseData>
}