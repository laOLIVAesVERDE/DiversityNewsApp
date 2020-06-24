package com.oliva.verde.android.divercitynewsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/everything/")
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Call<ResponseData>
}