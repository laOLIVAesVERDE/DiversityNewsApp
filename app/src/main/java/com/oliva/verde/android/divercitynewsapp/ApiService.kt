package com.oliva.verde.android.divercitynewsapp

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/everything")
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("firstSearchWord") firstSearchWord : String,
                @Query("secondSearchWord") secondSearchWord : String)
}